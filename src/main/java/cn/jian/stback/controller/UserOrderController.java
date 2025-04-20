package cn.jian.stback.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jian.stback.bo.UserPO;
import cn.jian.stback.common.AccountType;
import cn.jian.stback.common.ActionType;
import cn.jian.stback.common.BaseStatus;
import cn.jian.stback.common.R;
import cn.jian.stback.common.TradeType;
import cn.jian.stback.common.TransType;
import cn.jian.stback.common.ZjException;
import cn.jian.stback.entity.UserOrder;
import cn.jian.stback.entity.UserWallet;
import cn.jian.stback.service.UserOrderService;
import cn.jian.stback.service.UserWalletService;

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

	@Autowired
	UserWalletService walletService;

	@RequestMapping("list")
	public R List(@RequestBody UserPO user) {
		return R.success(getList(user));
	}

	@RequestMapping("finish/{id}")
	@Transactional
	public R finish(@PathVariable Integer id) {
		UserOrder order = orderService.getById(id);
		if (!order.getStatus().equals(BaseStatus.disable.getValue())
				|| order.getOrderType().equals(BaseStatus.disable.getValue())) {
			throw new ZjException("该订单状态不可改");
		}
		order.setStatus(BaseStatus.enable.getValue());
		orderService.updateById(order);
		if (order.getType().equals(TradeType.buy.name())) {
			UserWallet coinWallet = walletService.getAmount(AccountType.trade.name(), order.getName(),
					order.getUserId());
			walletService.updateAmount(coinWallet, TransType.trade, ActionType.add, order.getNum());
		}
		if (order.getType().equals(TradeType.sell.name())) {
			UserWallet usdtWallet = walletService.getAmount(AccountType.trade.name(), "USDT", order.getUserId());
			walletService.updateAmount(usdtWallet, TransType.trade, ActionType.add, order.getAmount());
		}
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
