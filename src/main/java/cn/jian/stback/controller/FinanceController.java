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
import cn.jian.stback.entity.WalletLog;
import cn.jian.stback.service.WalletLogService;

@RestController
@RequestMapping("/finance")
public class FinanceController {

	@Autowired
	WalletLogService logService;

	@RequestMapping("list")
	public R getList(@RequestBody UserPO po) {
		System.out.println(po.getCurrent());
		QueryWrapper<WalletLog> wrapper = new QueryWrapper<WalletLog>();
		wrapper.orderByDesc("create_time");
		if (StringUtils.isNotBlank(po.getUserId())) {
			wrapper.eq("user_id", po.getUserId());
		}
		Page<WalletLog> page = new Page<>(po.getCurrent(), 10);
		page = logService.page(page, wrapper);
		return R.success(page);
	}

}
