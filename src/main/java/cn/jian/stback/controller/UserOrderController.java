package cn.jian.stback.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jian.stback.bo.UserPO;
import cn.jian.stback.common.R;
import cn.jian.stback.entity.UserOrder;
import cn.jian.stback.service.UserOrderService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jian
 * @since 2025-02-07
 */
@RestController
@RequestMapping("/order")
public class UserOrderController {

	@Autowired
	UserOrderService orderService;

	
	@RequestMapping("list")
	public R List(@RequestBody UserPO user) {
		return R.success(getList(user));
	}
	
	@RequestMapping("finish/{id}")
	public R finish(@PathVariable Integer id) {
		return R.success();
	}

	public Page<UserOrder> getList(UserPO po) {
		QueryWrapper<UserOrder> wrapper = new QueryWrapper<UserOrder>();
		if (StringUtils.isNotBlank(po.getUserId())) {
			wrapper.eq("user_id", po.getUserId());
		}
		if (StringUtils.isNotBlank(po.getStatus())) {
			wrapper.eq("status", po.getStatus());
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
		Page<UserOrder> page = new Page<>(po.getCurrent(), 10);
		page = orderService.page(page, wrapper);	
		return page;
	}

}
