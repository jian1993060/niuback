package cn.jian.stback.bo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserPO {

	int current = 1;

	String email;
	
	String name;

	LocalDate startDate;

	LocalDate endDate;

	String real;
	
	String userId;
	
	String status;
	
	String code;
	
    String id;
    
    String address;

}
