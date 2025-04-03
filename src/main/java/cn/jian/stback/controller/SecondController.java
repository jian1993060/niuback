package cn.jian.stback.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jian.stback.bo.UserPO;
import cn.jian.stback.common.R;
import cn.jian.stback.entity.SecondOrder;
import cn.jian.stback.entity.SecondProduct;
import cn.jian.stback.service.SecondOrderService;
import cn.jian.stback.service.SecondProductService;

@RestController
@RequestMapping("/second")
public class SecondController {

	@Autowired
	SecondProductService secondProductService;

	@Autowired
	SecondOrderService secondOrderService;

	@RequestMapping("list")
	public R list(@RequestBody UserPO po) {
		Page<SecondProduct> page = new Page<>(po.getCurrent(), 10);
		page.addOrder(OrderItem.ascs("order_num"));
		page = secondProductService.page(page);
		return R.success(page);
	}

	@RequestMapping("order/list")
	public R orderList(@RequestBody UserPO po) {
		return R.success(getList(po));
	}

	public Page<SecondOrder> getList(UserPO po) {
		QueryWrapper<SecondOrder> wrapper = new QueryWrapper<SecondOrder>();
		if (StringUtils.isNotBlank(po.getUserId())) {
			wrapper.eq("user_id", po.getUserId());
		}
		if (StringUtils.isNotBlank(po.getName())) {
			wrapper.like("name", po.getName());
		}
		if (po.getStartDate() != null) {
			wrapper.ge("create_time", po.getStartDate().atStartOfDay());
		}
		if (po.getEndDate() != null) {
			wrapper.le("create_time", po.getEndDate().plusDays(1).atStartOfDay());
		}
		wrapper.orderByDesc("create_time");
		Page<SecondOrder> page = new Page<>(po.getCurrent(), 10);
		page = secondOrderService.page(page, wrapper);
		return page;
	}

	@RequestMapping("update")
	@Transactional
	public R update(@RequestBody SecondProduct bo) {
		List<SecondProduct> list = secondProductService.list();
		// 修改一定要在前面，因为新增会修改id
		if (bo.getId() != null) {
			SecondProduct current = secondProductService.getById(bo.getId());
			if (bo.getOrderNum() > list.size()) {
				bo.setOrderNum(list.size());
			}
			secondProductService.updateById(bo);
			// 排序改变，修改排序
			if (current.getOrderNum() != bo.getOrderNum()) {
				updateOrder(list, bo, current.getOrderNum(), bo.getOrderNum() > current.getOrderNum());
			}
		}
		// 新增
		if (bo.getId() == null) {
			if (bo.getOrderNum() > list.size() + 1) {
				bo.setOrderNum(list.size() + 1);
			}
			bo.setMaxAmount(new BigDecimal("99999999"));
			secondProductService.save(bo);
			// 不是最后一位，修改排序
			if (bo.getOrderNum() != list.size() + 1) {
				addOrder(list, bo);
			}

		}
		return R.success();
	}

	private void updateOrder(List<SecondProduct> list, SecondProduct bo, int oldNum, boolean max) {
		List<SecondProduct> upList = new ArrayList<SecondProduct>();
		for (SecondProduct pro : list) {
			if (!pro.getId().equals(bo.getId())) {
				/// 如果排序变大，比如2>4，那么比2大的才动。比4大的不动 所以动的是>2 并且<=4的 -1
				if (pro.getOrderNum() > oldNum && max && pro.getOrderNum() <= bo.getOrderNum()) {
					pro.setOrderNum(pro.getOrderNum() - 1);
					upList.add(pro);
				}
				if (pro.getOrderNum() < oldNum && !max && pro.getOrderNum() >= bo.getOrderNum()) {
					pro.setOrderNum(pro.getOrderNum() + 1);
					upList.add(pro);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(upList)) {
			secondProductService.updateBatchById(upList);
		}

	}

	private void addOrder(List<SecondProduct> list, SecondProduct bo) {
		List<SecondProduct> upList = new ArrayList<SecondProduct>();
		for (SecondProduct pro : list) {
			if (pro.getOrderNum() >= bo.getOrderNum()) {
				pro.setOrderNum(pro.getOrderNum() + 1);
				upList.add(pro);
			}
		}
		if (CollectionUtils.isNotEmpty(upList)) {
			secondProductService.updateBatchById(upList);
		}

	}

	@RequestMapping("del/{id}")
	@Transactional
	public R del(@PathVariable Integer id) {
		SecondProduct pro = secondProductService.getById(id);
		secondProductService.removeById(pro);
		delOrder(pro);
		return R.success();
	}

	private void delOrder(SecondProduct bo) {
		List<SecondProduct> list = secondProductService.list();
		List<SecondProduct> upList = new ArrayList<SecondProduct>();
		for (SecondProduct pro : list) {
			if (!pro.getId().equals(bo.getId())) {
				if (pro.getOrderNum() > bo.getOrderNum()) {
					pro.setOrderNum(pro.getOrderNum() - 1);
					upList.add(pro);
				}
			}
		}

		secondProductService.updateBatchById(upList);

	}

}
