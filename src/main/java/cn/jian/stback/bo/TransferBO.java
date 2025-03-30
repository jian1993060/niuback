package cn.jian.stback.bo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransferBO {

	String name;

	String idNo;
	
	String phone;

	BigDecimal amount;
	
	String code;
}
