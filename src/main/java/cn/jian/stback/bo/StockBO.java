package cn.jian.stback.bo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class StockBO {

	@NotEmpty
	String name;

	@NotEmpty
	String logo;

}
