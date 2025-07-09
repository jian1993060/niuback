package cn.jian.stback.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jian.stback.bo.StockBO;
import cn.jian.stback.bo.UserPO;
import cn.jian.stback.common.AccountType;
import cn.jian.stback.common.R;
import cn.jian.stback.entity.StockData;
import cn.jian.stback.entity.User;
import cn.jian.stback.entity.UserWallet;
import cn.jian.stback.service.StockDataService;
import cn.jian.stback.service.UserService;
import cn.jian.stback.service.UserWalletService;
import cn.jian.stback.util.OkHttpUtil;
import cn.jian.stback.util.StockUtil;

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

	@Autowired
	UserWalletService walletService;

	@Autowired
	UserService userService;

	@RequestMapping("list")
	public R List(@RequestBody UserPO user) {
		return R.success(getList(user));
	}

	@RequestMapping("create")
	public R create(@RequestBody @Validated StockBO bo) throws Exception {
		String code = bo.getCode();
		StockData data = stockDataService.getById(code);
		if (data != null) {
			return R.error("该股票已经存在");
		}
		if (!StockUtil.getNewPrice(code, bo.getType())) {
			return R.error("该股票不存在或者延迟，请再次确认");
		}
		data = new StockData();
		data.setName(bo.getName());
		data.setId(code);
		data.setType(bo.getType());
		data.setStatus("1");
		data.setOrderNum(6);
		data.setLogo(bo.getLogo());
		String resp = OkHttpUtil.sendPost("http://127.0.0.1:10022/stock/back/up", JSON.toJSONString(data));
		if (!JSON.parseObject(resp).getString("code").equals("0000")) {
			return R.error("未知错误");
		}
		stockDataService.save(data);
		return R.success();
	}

	@RequestMapping("refresh/{id}")
	@Transactional
	public R refresh(@PathVariable String id) throws Exception {
		QueryWrapper<UserWallet> q = new QueryWrapper<UserWallet>();
		q.eq("code", id);
		walletService.remove(q);
		StockData stock = stockDataService.getById(id);
		List<UserWallet> wallets = new ArrayList<>();
		List<User> users = userService.list();
		for (User user : users) {
			UserWallet userWalletTrade = new UserWallet();
			userWalletTrade.setAmount(BigDecimal.ZERO);
			userWalletTrade.setUserId(user.getId());
			userWalletTrade.setCode(stock.getId());
			userWalletTrade.setName(stock.getName());
			userWalletTrade.setVersion(1);
			userWalletTrade.setType(AccountType.trade.name());
			wallets.add(userWalletTrade);
		}
		walletService.saveOrUpdateBatch(wallets);
		return R.success();
	}

	public Page<StockData> getList(UserPO po) {
		QueryWrapper<StockData> wrapper = new QueryWrapper<StockData>();
		if (StringUtils.isNotBlank(po.getId())) {
			wrapper.like("id", po.getId());
		}
		if (StringUtils.isNotBlank(po.getName())) {
			wrapper.like("name", po.getName());
		}
		wrapper.orderByAsc("order_num");
		Page<StockData> page = new Page<>(po.getCurrent(), 10);
		page = stockDataService.page(page, wrapper);
		return page;
	}

}
