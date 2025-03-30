package cn.jian.stback.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jian.stback.bo.UserPO;
import cn.jian.stback.common.R;
import cn.jian.stback.entity.Recharge;
import cn.jian.stback.entity.StockData;
import cn.jian.stback.service.RechargeService;
import cn.jian.stback.service.StockDataService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jian
 * @since 2025-02-10
 */
@RestController
@RequestMapping("/recharge")
public class RechargeController {

	@Autowired
	RechargeService chargeService;

	@RequestMapping("list")
	public R List(@RequestBody UserPO user) {
		return R.success(getList(user));
	}

	public Page<Recharge> getList(UserPO po) {
		QueryWrapper<Recharge> wrapper = new QueryWrapper<Recharge>();
		if (StringUtils.isNotBlank(po.getUserId())) {
			wrapper.eq("user_id", po.getUserId());
		}
		if (po.getStartDate() != null) {
			wrapper.ge("create_time", po.getStartDate().atStartOfDay());
		}
		if (po.getEndDate() != null) {
			wrapper.le("create_time", po.getEndDate().plusDays(1).atStartOfDay());
		}
		wrapper.orderByAsc("create_time");
		Page<Recharge> page = new Page<>(po.getCurrent(), 10);
		page = chargeService.page(page, wrapper);
		return page;
	}
}
