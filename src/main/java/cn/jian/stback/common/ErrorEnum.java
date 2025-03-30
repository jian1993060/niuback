package cn.jian.stback.common;

/**
 * 默认异常
 *
 * @author y3tu
 */
public enum ErrorEnum implements IError {
	/**
	 * 系统内部错误
	 */
	SYSTEM_INTERNAL_ERROR("SYS-ERROR-001", "系统内部错误"),
	/**
	 * 服务调用异常
	 */
	SERVICE_CALL_ERROR("SYS-ERROR-002", "服务调用异常"),
	/**
	 * 未发现服务
	 */
	SERVICE_NOT_FOUND("SYS-ERROR-003", "未发现服务"),
	/**
	 * 服务运行SQLException异常
	 */
	SQL_EXCEPTION("SYS-ERROR-004", "数据库异常"),
	/**
	 * 系统繁忙,请稍候再试
	 */
	SYSTEM_BUSY("SYS-ERROR-005", "系统繁忙,请稍候再试"),
	/**
	 * Tool工具异常
	 */
	UTIL_EXCEPTION("SYS-ERROR-006", "工具异常"),

	NO_LOGIN("SYS-ERROR-007", "用户未登录"),
	
	INVID_CODE("SYS-ERROR-008", "CODE失效"),
	
	PARAM_ERRO("SYS-ERROR-009", "参数异常");

	String code;
	String msg;

	ErrorEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getMsg() {
		return this.msg;
	}
}
