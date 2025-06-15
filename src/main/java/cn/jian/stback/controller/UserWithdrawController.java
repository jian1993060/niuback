package cn.jian.stback.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jian.stback.bo.ShPO;
import cn.jian.stback.bo.UserPO;
import cn.jian.stback.common.AccountType;
import cn.jian.stback.common.ActionType;
import cn.jian.stback.common.BaseStatus;
import cn.jian.stback.common.R;
import cn.jian.stback.common.TransType;
import cn.jian.stback.common.ZjException;
import cn.jian.stback.entity.UserWallet;
import cn.jian.stback.entity.UserWithdraw;
import cn.jian.stback.service.UserService;
import cn.jian.stback.service.UserWalletService;
import cn.jian.stback.service.UserWithdrawService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jian
 * @since 2025-02-07
 */
@RestController
@RequestMapping("/withdraw")
public class UserWithdrawController {

	@Autowired
	UserWalletService walletService;
	
	@Autowired
	UserWithdrawService withdrawService;
	
	@Autowired
	UserService userService;

	@RequestMapping("list")
	public R List(@RequestBody UserPO user) {
		return R.success(getList(user));
	}

	public Page<UserWithdraw> getList(UserPO po) {
		QueryWrapper<UserWithdraw> wrapper = new QueryWrapper<UserWithdraw>();
		if (StringUtils.isNotBlank(po.getUserId())) {
			wrapper.eq("user_id", po.getUserId());
		}
		if (StringUtils.isNotBlank(po.getAddress())) {
			wrapper.like("address", po.getAddress());
		}
		if (StringUtils.isNotBlank(po.getStatus())) {
			wrapper.like("status", po.getStatus());
		}
		if (po.getStartDate() != null) {
			wrapper.ge("create_time", po.getStartDate().atStartOfDay());
		}
		if (po.getEndDate() != null) {
			wrapper.le("create_time", po.getEndDate().plusDays(1).atStartOfDay());
		}
		wrapper.orderByAsc("create_time");
		Page<UserWithdraw> page = new Page<>(po.getCurrent(), 10);
		page = withdrawService.page(page, wrapper);
		return page;
	}
	
	@RequestMapping("finish")
	@Transactional
	public R finish(@RequestBody ShPO po) throws Exception {
		UserWithdraw withdraw = withdrawService.getById(po.getId());
		if (!withdraw.getStatus().equals(BaseStatus.verify.getValue())) {
			throw new ZjException("该订单已经完成");
		}
		if (po.getStatus().equals(BaseStatus.enable.getValue())) {
			withdraw.setStatus(BaseStatus.enable.getValue());
			withdrawService.updateById(withdraw);
		}
		if (po.getStatus().equals(BaseStatus.disable.getValue())) {
			if (StringUtils.isBlank(po.getInfo())) {
				throw new ZjException("驳回理由必填");
			}
			withdraw.setRefuseInfo(po.getInfo());
			withdraw.setStatus(po.getStatus());
			UserWallet wallet = walletService.getAmount(AccountType.trade.name(), withdraw.getCoinType(),
					withdraw.getUserId());
			walletService.updateAmount(wallet, TransType.withdrawal, ActionType.add, withdraw.getAmount());  
			withdrawService.updateById(withdraw);
		}
		return R.success();
	}

}
