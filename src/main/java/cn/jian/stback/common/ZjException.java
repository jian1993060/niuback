package cn.jian.stback.common;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义exp
 * 
 * @author jiang
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ZjException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String code;

	String msg;

	ErrorEnum erenum;

	public ZjException(String code, String msg) {
		super(msg);
		this.code = code;
	}
	/**
	 * 参数异常的定义
	 * @param code
	 * @param msg
	 */
	public ZjException(String msg) {
		super(msg);
		this.code = ErrorEnum.PARAM_ERRO.getCode();
	}

	public ZjException(ErrorEnum erenum) {
		super(erenum.getMsg());
		this.code = erenum.getCode();
		this.msg = erenum.getMsg();
	}

	public ZjException(String code, String msg, Throwable cause) {
		super(msg, cause);
		this.code = code;
	}

}
