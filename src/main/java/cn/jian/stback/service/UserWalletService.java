package cn.jian.stback.service;



import java.math.BigDecimal;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.jian.stback.common.ActionType;
import cn.jian.stback.common.TransType;
import cn.jian.stback.entity.UserWallet;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jian
 * @since 2025-01-23
 */
public interface UserWalletService extends IService<UserWallet> {

	public UserWallet getAmount(String type, String code,Integer userId);

	// 修改用户钱包并且记录操作日志 采用version乐观锁防止并发错误
	public void updateAmount(UserWallet wallet, TransType trans, ActionType aciton, BigDecimal amount);
}
