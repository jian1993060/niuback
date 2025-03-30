package cn.jian.stback.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtUser {

	private String id;

	private String token;
	
	private String userType;

	private boolean code;
		
}