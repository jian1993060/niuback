package cn.jian.stback.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson2.JSON;

import cn.jian.stback.common.ErrorEnum;
import cn.jian.stback.common.JwtUser;
import cn.jian.stback.common.R;
import cn.jian.stback.util.TokenUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
	/***
	 * 在请求处理之前进行调用(Controller方法调用之前)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("token");
		JwtUser jwtUser = TokenUtil.parseJWT(token);
		if (!jwtUser.isCode()) {
			returnNoLogin(response);
			return false;
		} else {
			request.setAttribute("user", jwtUser);
			return true;
		}

	}

	/**
	 * 返回未登录的错误信息
	 * 
	 * @param response ServletResponse
	 * @throws IOException 
	 */
	private void returnNoLogin(HttpServletResponse response) throws IOException {
		ServletOutputStream outputStream = response.getOutputStream();
		// 设置返回401 和响应编码
		response.setStatus(401);
		response.setContentType("Application/json;charset=utf-8");
		String code = JSON.toJSONString(R.error(ErrorEnum.NO_LOGIN));
		outputStream.write(code.getBytes(StandardCharsets.UTF_8));
	}

	/***
	 * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/***
	 * 整个请求结束之后被调用，也就是在DispatchServlet渲染了对应的视图之后执行（主要用于进行资源清理工作）
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
