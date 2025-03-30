package cn.jian.stback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.jian.stback.bo.LoginPO;
import cn.jian.stback.common.JwtUser;
import cn.jian.stback.common.R;
import cn.jian.stback.common.ZjException;
import cn.jian.stback.entity.SystemUesr;
import cn.jian.stback.service.SystemUesrService;
import cn.jian.stback.util.TokenUtil;

@RestController
public class LoginController {

	@Autowired
	SystemUesrService systemUesrService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	

	@RequestMapping("login")
	public R login(@RequestBody LoginPO loginPO) {
		QueryWrapper<SystemUesr> qWrapper = new QueryWrapper<SystemUesr>();
		qWrapper.eq("name", loginPO.getName());
		SystemUesr user = systemUesrService.getOne(qWrapper);
		if (user == null) {
			throw new ZjException("密码不对");
		}
		if (!passwordEncoder.matches(loginPO.getPassword(), user.getPassword())) {
			throw new ZjException("密码不对");
		}
		JwtUser jwtUser = new JwtUser(user.getId(), "", user.getUserType(), false);
		TokenUtil.createJWT(jwtUser);
		return R.success(jwtUser);
	}
	

}
