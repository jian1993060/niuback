package cn.jian.stback.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserBO {

	@NotNull
	Integer id;

	@NotNull
	String type;
}
