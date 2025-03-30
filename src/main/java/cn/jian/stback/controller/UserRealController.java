package cn.jian.stback.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.jian.stback.bo.ShPO;
import cn.jian.stback.bo.UserPO;
import cn.jian.stback.common.R;
import cn.jian.stback.common.ZjException;
import cn.jian.stback.entity.User;
import cn.jian.stback.entity.UserReal;
import cn.jian.stback.service.UserRealService;
import cn.jian.stback.service.UserService;
import cn.jian.stback.service.UserWalletService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jian
 * @since 2025-02-10
 */
@RestController
@RequestMapping("/real")
public class UserRealController {

	@Autowired
	UserRealService realService;

	@Autowired
	UserService userService;

	@Autowired
	UserWalletService userWalletService;

	/**
	 * 查询用户
	 */
	@RequestMapping("list")
	public R List(@RequestBody UserPO user) {
		return R.success(getList(user));
	}

	public Page<UserReal> getList(UserPO po) {
		QueryWrapper<UserReal> wrapper = new QueryWrapper<UserReal>();

		if (StringUtils.isNotBlank(po.getUserId())) {
			wrapper.eq("user_id", po.getUserId());
		}
		if (StringUtils.isNotBlank(po.getStatus())) {		
			wrapper.eq("status", po.getStatus());
			
		}
		if (po.getStartDate() != null) {
			wrapper.ge("create_time", po.getStartDate().atStartOfDay());
		}
		if (po.getEndDate() != null) {
			wrapper.le("create_time", po.getEndDate().plusDays(1).atStartOfDay());
		}
		wrapper.orderByAsc("create_time");
		Page<UserReal> page = new Page<>(po.getCurrent(), 10);
		page = realService.page(page, wrapper);
		return page;
	}

	@RequestMapping("finish")
	@Transactional
	public R finish(@RequestBody ShPO po) throws Exception {
		UserReal real = realService.getById(po.getId());
		if (!real.getStatus().equals("3")) {
			throw new ZjException("该订单已经完成");
		}
		if (po.getStatus().equals("1")) {
			User user = userService.getById(real.getUserId());
			user.setRealStatus("1");
			userService.updateById(user);
			real.setStatus(po.getStatus());
			realService.updateById(real);
		}
		if (po.getStatus().equals("2")) {
			if (StringUtils.isBlank(po.getInfo())) {
				throw new ZjException("驳回理由必填");
			}
			real.setRefuseInfo(po.getInfo());
			real.setStatus(po.getStatus());
			realService.updateById(real);
		}
		return R.success();
	}

}
