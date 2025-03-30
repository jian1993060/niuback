package cn.jian.stback.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsdtBO {
	
	String from;

	String to;

	String value;
	
	String hash;
	
}
