package cn.jian.stback.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jian.stback.bo.ShPO;
import cn.jian.stback.bo.UserPO;
import cn.jian.stback.common.R;
import cn.jian.stback.common.ZjException;
import cn.jian.stback.config.MerchantDataUtil;
import cn.jian.stback.entity.Recharge;
import cn.jian.stback.entity.User;
import cn.jian.stback.entity.UserReal;
import cn.jian.stback.entity.UserWallet;
import cn.jian.stback.entity.UserWithdraw;
import cn.jian.stback.service.RechargeService;
import cn.jian.stback.service.UserWalletService;
import cn.jian.stback.service.UserWithdrawService;
import cn.jian.stback.util.AccountUtil;

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
	UserWalletService UserWalletService;
	
	@Autowired
	UserWithdrawService withdrawService;


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
	
//	@RequestMapping("finish")
//	@Transactional
//	public R finish(@RequestBody ShPO po) throws Exception {
//		UserReal real = realService.getById(po.getId());
//		if (!real.getStatus().equals("3")) {
//			throw new ZjException("该订单已经完成");
//		}
//		if (po.getStatus().equals("1")) {
//			User user = userService.getById(real.getUserId());
//			user.setRealStatus("1");
//			userService.updateById(user);
//			UserWallet wallet = userWalletService.getById(real.getUserId());
//			wallet.setIdNo(real.getIdNo());
//			wallet.setName(real.getName());
//			ECKeyPair ecKeyPair = Keys.createEcKeyPair();// 调用Keys的静态方法创建密钥对
//			String privateKey = "0x" + ecKeyPair.getPrivateKey().toString(16);// 获取私钥
//			String address = Keys.getAddress(ecKeyPair.getPublicKey().toString(16));// 获取地址值
//			wallet.setPrivateKey(AccountUtil.encode(privateKey));
//			wallet.setAddress("0x" + address);
//			userWalletService.updateById(wallet);
//			real.setStatus(po.getStatus());
//			realService.updateById(real);
//			MerchantDataUtil.wallets.put(wallet.getAddress(), wallet);
//		}
//		if (po.getStatus().equals("2")) {
//			if (StringUtils.isBlank(po.getInfo())) {
//				throw new ZjException("驳回理由必填");
//			}
//			real.setRefuseInfo(po.getInfo());
//			real.setStatus(po.getStatus());
//			realService.updateById(real);
//		}
//		return R.success();
//	}

}
