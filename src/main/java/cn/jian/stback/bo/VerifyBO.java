package cn.jian.stback.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerifyBO {
	
	@NotNull
	Integer id;
	
	@NotNull
	String status;
	
	String info;

}
