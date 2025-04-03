package cn.jian.stback.controller;

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
import cn.jian.stback.entity.CloudOrder;
import cn.jian.stback.entity.CloudProduct;
import cn.jian.stback.service.CloudOrderService;
import cn.jian.stback.service.CloudProductService;
import cn.jian.stback.service.UserWalletService;

@RestController
@RequestMapping("/cloud")
public class CloudController {

	@Autowired
	CloudOrderService cloudOrderService;

	@Autowired
	CloudProductService cloudProductService;

	@Autowired
	UserWalletService walletService;

	@RequestMapping("list")
	public R list(@RequestBody UserPO po) {
		Page<CloudProduct> page = new Page<>(po.getCurrent(), 10);
		page.addOrder(OrderItem.ascs("order_num"));
		page = cloudProductService.page(page);
		return R.success(page);
	}

	@RequestMapping("order/list")
	public R orderList(@RequestBody UserPO po) {
		return R.success(getList(po));
	}

	public Page<CloudOrder> getList(UserPO po) {
		QueryWrapper<CloudOrder> wrapper = new QueryWrapper<CloudOrder>();
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
		Page<CloudOrder> page = new Page<>(po.getCurrent(), 10);
		page = cloudOrderService.page(page, wrapper);
		return page;
	}

	@RequestMapping("update")
	@Transactional
	public R update(@RequestBody CloudProduct bo) {
		List<CloudProduct> list = cloudProductService.list();
		// 修改一定要在前面，因为新增会修改id
		if (bo.getId() != null) {
			CloudProduct current = cloudProductService.getById(bo.getId());
			if (bo.getOrderNum() > list.size()) {
				bo.setOrderNum(list.size());
			}
			cloudProductService.updateById(bo);
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
			bo.setMaxNum(99999999);
			cloudProductService.save(bo);
			// 不是最后一位，修改排序
			if (bo.getOrderNum() != list.size() + 1) {
				addOrder(list, bo);
			}

		}
		return R.success();
	}

	private void updateOrder(List<CloudProduct> list, CloudProduct bo, int oldNum, boolean max) {
		List<CloudProduct> upList = new ArrayList<CloudProduct>();
		for (CloudProduct pro : list) {
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
			cloudProductService.updateBatchById(upList);
		}

	}

	private void addOrder(List<CloudProduct> list, CloudProduct bo) {
		List<CloudProduct> upList = new ArrayList<CloudProduct>();
		for (CloudProduct pro : list) {
			if (pro.getOrderNum() >= bo.getOrderNum()) {
				pro.setOrderNum(pro.getOrderNum() + 1);
				upList.add(pro);
			}
		}
		if (CollectionUtils.isNotEmpty(upList)) {
			cloudProductService.updateBatchById(upList);
		}

	}

	@RequestMapping("del/{id}")
	@Transactional
	public R del(@PathVariable Integer id) {
		CloudProduct pro = cloudProductService.getById(id);
		cloudProductService.removeById(pro);
		delOrder(pro);
		return R.success();
	}

	private void delOrder(CloudProduct bo) {
		List<CloudProduct> list = cloudProductService.list();
		List<CloudProduct> upList = new ArrayList<CloudProduct>();
		for (CloudProduct pro : list) {
			if (!pro.getId().equals(bo.getId())) {
				if (pro.getOrderNum() > bo.getOrderNum()) {
					pro.setOrderNum(pro.getOrderNum() - 1);
					upList.add(pro);
				}
			}
		}
		cloudProductService.updateBatchById(upList);

	}

}
