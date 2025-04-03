package cn.jian.stback.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.jian.stback.common.ActionType;
import cn.jian.stback.common.TransType;
import cn.jian.stback.common.ZjException;
import cn.jian.stback.entity.UserWallet;
import cn.jian.stback.entity.WalletLog;
import cn.jian.stback.mapper.UserWalletMapper;
import cn.jian.stback.service.UserWalletService;
import cn.jian.stback.service.WalletLogService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jian
 * @since 2025-01-23
 */
@Service
public class UserWalletServiceImpl extends ServiceImpl<UserWalletMapper, UserWallet> implements UserWalletService {

	@Autowired
	WalletLogService walletLogService;

	@Override
	public UserWallet getAmount(String type, String name, Integer userId) {
		QueryWrapper<UserWallet> q = new QueryWrapper<>();
		q.eq("user_id", userId).eq("name", name).eq("type", type);
		UserWallet wallet = getOne(q);
		return wallet;
	}

	@Override
	public void updateAmount(UserWallet wallet, TransType trans, ActionType aciton, BigDecimal amount) {
		String info = "";
		BigDecimal beforAmount = wallet.getAmount();
		BigDecimal afterAmount = null;
		if (aciton.equals(ActionType.add)) {
			afterAmount = beforAmount.add(amount);
			info = "+" + amount;
		}
		if (aciton.equals(ActionType.sub)) {
			afterAmount = beforAmount.subtract(amount);
			info = "-" + amount;
		}
		if (afterAmount.compareTo(BigDecimal.ZERO) < 0) {
			throw new ZjException("system error");
		}
		wallet.setAmount(afterAmount);
		updateById(wallet);
		WalletLog log = new WalletLog();
		log.setAfterAmount(afterAmount);
		log.setBeforeAmount(beforAmount);
		log.setName(wallet.getName());
		log.setCreateTime(LocalDateTime.now());
		log.setTransResult(info);
		log.setType(trans.name());
		log.setUserId(wallet.getUserId());
		log.setWalletType(wallet.getType());
		walletLogService.save(log);
	}

}
