package cn.jian.stback.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jian.stback.bo.UserPO;
import cn.jian.stback.bo.VerifyBO;
import cn.jian.stback.common.AccountType;
import cn.jian.stback.common.ActionType;
import cn.jian.stback.common.BaseStatus;
import cn.jian.stback.common.R;
import cn.jian.stback.common.TransType;
import cn.jian.stback.common.ZjException;
import cn.jian.stback.entity.Recharge;
import cn.jian.stback.entity.StockData;
import cn.jian.stback.entity.UserWallet;
import cn.jian.stback.service.RechargeService;
import cn.jian.stback.service.StockDataService;
import cn.jian.stback.service.UserWalletService;

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

	@Autowired
	UserWalletService walletService;

	@RequestMapping("list")
	public R List(@RequestBody UserPO user) {
		return R.success(getList(user));
	}

	@RequestMapping("verify")
	@Transactional
	public R List(@RequestBody @Validated VerifyBO bo) {
		Recharge recharge = chargeService.getById(bo.getId());
		if (!recharge.getStatus().equals(BaseStatus.verify.getValue())) {
			throw new ZjException("状态不对");
		}
		if (bo.getStatus().equals(BaseStatus.enable.getValue())) {
			UserWallet coinWallet = walletService.getAmount(AccountType.trade.name(), recharge.getName(),
					recharge.getUserId());
			walletService.updateAmount(coinWallet, TransType.recharge, ActionType.add, recharge.getAmount());
			recharge.setStatus(bo.getStatus());
			chargeService.updateById(recharge);
		}
		if (bo.getStatus().equals(BaseStatus.disable.getValue())) {
			recharge.setStatus(bo.getStatus());
			recharge.setInfo(bo.getInfo());
			chargeService.updateById(recharge);
		}
		return R.success();
	}

	public Page<Recharge> getList(UserPO po) {
		QueryWrapper<Recharge> wrapper = new QueryWrapper<Recharge>();
		if (StringUtils.isNotBlank(po.getUserId())) {
			wrapper.eq("user_id", po.getUserId());
		}
		if (StringUtils.isNotBlank(po.getStatus())) {
			wrapper.eq("status", po.getStatus());
		}
		if (po.getStartDate() != null) {
			wrapper.ge("create_time", po.getStartDate().atStartOfDay());
		}
		if (po.getEndDate() != null) {
			wrapper.le("create_time", po.getEndDate().plusDays(1).atStartOfDay());
		}
		wrapper.orderByDesc("create_time");
		Page<Recharge> page = new Page<>(po.getCurrent(), 10);
		page = chargeService.page(page, wrapper);
		return page;
	}
}
