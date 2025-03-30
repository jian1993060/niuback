package cn.jian.stback.config;

import java.util.Map;
import java.util.TreeMap;

import cn.jian.stback.entity.UserWallet;


public class MerchantDataUtil {

	
	public static Map<String, UserWallet> wallets = new TreeMap<String, UserWallet>(String.CASE_INSENSITIVE_ORDER);;

}
