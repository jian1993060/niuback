package cn.jian.stback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jian.stback.bo.UserPO;
import cn.jian.stback.common.R;
import cn.jian.stback.entity.SecondProduct;
import cn.jian.stback.service.SecondProductService;

@RestController
@RequestMapping("/second")
public class SecondController {

	@Autowired
	SecondProductService secondProductService;

	@RequestMapping("list")
	public R List(@RequestBody UserPO po) {
		Page<SecondProduct> page = new Page<>(po.getCurrent(), 10);
		page.addOrder(OrderItem.ascs("order_num"));
		page = secondProductService.page(page);
		return R.success(page);
	}

}
