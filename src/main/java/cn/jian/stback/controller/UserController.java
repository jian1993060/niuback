package cn.jian.stback.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jian.stback.bo.UserBO;
import cn.jian.stback.bo.UserPO;
import cn.jian.stback.bo.WinType;
import cn.jian.stback.common.R;
import cn.jian.stback.entity.User;
import cn.jian.stback.service.UserService;
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
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserWalletService walletService;

	/**
	 * 查询用户
	 */
	@RequestMapping("list")
	public R List(@RequestBody UserPO user) {
		return R.success(getList(user));
	}

	@RequestMapping("update")
	public R update(@RequestBody UserBO bo) {
		User user = new User();
		user.setId(bo.getId());
		user.setType(WinType.valueOf(bo.getType()).name());
		userService.updateById(user);
		return R.success();
	}

	public Page<User> getList(UserPO po) {
		QueryWrapper<User> wrapper = new QueryWrapper<User>();
		if (StringUtils.isNotBlank(po.getEmail())) {
			wrapper.like("email", po.getEmail());
		}
		if (StringUtils.isNotBlank(po.getId())) {
			wrapper.eq("id", po.getId());
		}
		if (StringUtils.isNotBlank(po.getType())) {
			wrapper.eq("type", po.getType());
		}
		if (StringUtils.isNotBlank(po.getStatus())) {
			wrapper.eq("real_status", po.getStatus());
		}
		if (po.getStartDate() != null) {
			wrapper.ge("create_time", po.getStartDate().atStartOfDay());
		}
		if (po.getEndDate() != null) {
			wrapper.le("create_time", po.getEndDate().plusDays(1).atStartOfDay());
		}
		Page<User> page = new Page<>(po.getCurrent(), 10);
		page = userService.page(page, wrapper);
		return page;
	}

	@RequestMapping("info")
	public R info() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		list.add("admin");
		map.put("name", "admin");
		map.put("roles", list);
		return R.success(map);
	}
}
