package cn.jian.stback.bo;

import lombok.Data;

@Data
public class LoginPO {

	String name;
	
	String phone;

	String password;

	String type;
	
	String code;
	
	String invitCode;
}
