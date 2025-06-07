package cn.jian.stback.bo;

import lombok.Data;

@Data
public class StockApiResp {
	
	int ret;

	String trace;
	
	String msg;
	
	String data;
	
}
