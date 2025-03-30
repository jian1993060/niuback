package cn.jian.stback.config;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jian.stback.common.R;
import cn.jian.stback.common.ZjException;
import lombok.extern.java.Log;

@ControllerAdvice
@Log
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public R exceptionHandler(Exception e) {
		String info = getTrace(e);
		log.info(info);
		if (e instanceof ZjException) {
			return R.error(((ZjException) e).getCode(), e.getMessage());
		}
		return R.error("system error");
	}

	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}

}
