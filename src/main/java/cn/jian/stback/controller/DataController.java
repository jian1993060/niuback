package cn.jian.stback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jian.stback.bo.DataBO;
import cn.jian.stback.common.R;
import cn.jian.stback.mapper.UserOrderMapper;

@RestController
@RequestMapping("data")
public class DataController {

	@Autowired
	UserOrderMapper orderMapper;

	@RequestMapping("info")
	public R info() {
		DataBO bo = new DataBO();
//		bo.setBuyAmount(orderMapper.getBuyAmount());
//		bo.setOrderAmount(orderMapper.getOrderAmount());
//		bo.setOrderNum(orderMapper.getOrderNum());
//		bo.setRechargeAmount(orderMapper.getChargeAmount());
//		bo.setRechargeNum(orderMapper.getChargeNum());
//		bo.setSaleAmount(orderMapper.getSaleAmount());
//		bo.setWithdrawAmount(orderMapper.getWithdrawAmount());
//		bo.setWithdrawNum(orderMapper.getWithdrawNum());
		return R.success(bo);

	}
}
