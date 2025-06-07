package cn.jian.stback.bo;

import java.util.Map;

import lombok.Data;

@Data
public class StockApiReq {

	String trace;
	
	Map data;
}
