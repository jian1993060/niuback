package cn.jian.stback.bo;

import java.math.BigDecimal;
import java.util.List;
import cn.jian.stback.entity.CloudOrder;
import cn.jian.stback.entity.SecondOrder;
import cn.jian.stback.entity.User;
import cn.jian.stback.entity.UserOrder;
import cn.jian.stback.entity.UserWallet;
import lombok.Data;

@Data
public class UserVO {

	Integer id;

	User user;

	BigDecimal win;

	List<SecondOrder> secondOrders;

	List<UserOrder> tradeOrders;

	List<UserWallet> wallets;

	List<CloudOrder> cloudOrders;
}
