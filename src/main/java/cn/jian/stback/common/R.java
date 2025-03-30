package cn.jian.stback.common;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class R<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 失败错误码
	 */
	private String code;
	/**
	 * 返回消息
	 */
	private String msg;
	/**
	 * 返回结果对象
	 */
	private T data;
	/**
	 * 返回状态 成功,失败,警告 默认成功
	 */
	private Status result = Status.SUCCESS;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Status getResult() {
		return result;
	}

	public void setResult(Status result) {
		this.result = result;
	}

	public R() {
	}

	public R(String msg) {
		this.msg = msg;
	}

	public R(T data) {
		this.data = data;
	}

	public R(String msg, T data) {
		this.msg = msg;
		this.data = data;
	}

	public static R success() {
		R r = new R();
		r.code = "0000";
		r.setResult(Status.SUCCESS);
		r.setData("成功");
		return r;
	}

	public static R success(Object data) {
		R r = new R();
		r.code = "0000";
		r.setResult(Status.SUCCESS);
		r.setData(data);
		return r;
	}

	public static R success(String msg, Object... data) {
		R r = new R();
		r.setResult(Status.SUCCESS);
		r.setData(data);
		r.setMsg(msg);
		return r;
	}

	public static R warn() {
		R r = new R();
		r.setResult(Status.WARN);
		return r;
	}

	public static R warn(String msg, Object... data) {
		R r = new R();
		r.setResult(Status.WARN);
		r.setData(data);
		r.setMsg(msg);
		return r;
	}

	public static R error() {
		R r = R.error(ErrorEnum.SYSTEM_INTERNAL_ERROR);
		r.setResult(Status.ERROR);
		return r;
	}

	public static R error(String code, String message) {
		R r = new R();
		r.setResult(Status.ERROR);
		r.setCode(code);
		r.setMsg(message);
		return r;
	}

	public static R error(String message) {
		R r = new R();
		r.setCode(ErrorEnum.SYSTEM_INTERNAL_ERROR.getCode());
		r.setResult(Status.ERROR);
		r.setMsg(message);
		return r;
	}

	public static R error(String code, String message, Object data) {
		R r = new R();
		r.setResult(Status.ERROR);
		r.setCode(code);
		r.setMsg(message);
		r.setData(data);
		return r;
	}

	public static R error(int code, String message, Object data) {
		R r = new R();
		r.setResult(Status.ERROR);
		r.setCode(String.valueOf(code));
		r.setMsg(message);
		r.setData(data);
		return r;
	}

	public static R error(String message, IError error) {
		R r = new R();
		r.code = error.getCode();
		if (StringUtils.isNotEmpty(message)) {
			r.msg = message;
		} else {
			r.msg = error.getMsg();
		}
		r.result = Status.ERROR;
		return r;
	}

	public static R error(IError error) {
		R r = new R();
		r.code = error.getCode();
		r.msg = error.getMsg();
		r.result = Status.ERROR;
		return r;
	}

	public static enum Status {
		/**
		 * 状态
		 */
		SUCCESS, WARN, ERROR;

		Status() {
		}
	}
}
