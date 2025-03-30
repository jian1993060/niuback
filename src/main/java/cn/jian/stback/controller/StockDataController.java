package cn.jian.stback.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jian.stback.bo.UserPO;
import cn.jian.stback.common.R;
import cn.jian.stback.entity.StockData;
import cn.jian.stback.service.StockDataService;
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
@RequestMapping("/stock")
public class StockDataController {

	@Autowired
	StockDataService stockDataService;


	
	@RequestMapping("list")
	public R List(@RequestBody UserPO user) {
		return R.success(getList(user));
	}

	public Page<StockData> getList(UserPO po) {
		QueryWrapper<StockData> wrapper = new QueryWrapper<StockData>();
		if (StringUtils.isNotBlank(po.getId())) {
			wrapper.like("id", po.getId());
		}
		if (StringUtils.isNotBlank(po.getName())) {
			wrapper.like("name", po.getName());
		}
		wrapper.orderByAsc("status");
		Page<StockData> page = new Page<>(po.getCurrent(), 10);
		page = stockDataService.page(page, wrapper);
		return page;
	}

}
