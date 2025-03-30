package cn.jian.stback.bo;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class WithdrawPO {

	int current = 1;

	String status;

	String id;

	BigDecimal amount;

	String phone;

	String name;

	LocalDate startDate;

	LocalDate endDate;
}
