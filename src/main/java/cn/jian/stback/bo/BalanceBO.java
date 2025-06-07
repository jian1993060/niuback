package cn.jian.stback.bo;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BalanceBO {
	@NotNull
	Integer id;

	@NotNull
	BigDecimal balance;

	@NotNull
	String type;
}
