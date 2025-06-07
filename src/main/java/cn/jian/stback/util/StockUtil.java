package cn.jian.stback.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import cn.jian.stback.bo.StockApiReq;

/**
 * k线类型 1、1是1分钟K，2是5分钟K，3是15分钟K，4是30分钟K，5是小时K，6是2小时K(股票不支持2小时)，
 * 7是4小时K(股票不支持4小时)，8是日K，9是周K，10是月K （注：股票不支持2小时K、4小时K） 2、最短的k线只支持1分钟
 */
public class StockUtil {

	public static String token = "adf8070c9d2b729d2a660c50ccc0579d-c-app";

	public static String ALLTICK_URL = "https://quote.alltick.io/v2/quote-stock-b-api";

	public static boolean getNewPrice(String code) throws Exception {
		List<String> newCodes = new ArrayList<>();
		newCodes.add(code);
		String url = ALLTICK_URL + "/trade-tick?" + "token=" + token + "&query=";
		String str = getCodeParams(newCodes);
		String resp = OkHttpUtil.sendGet(url + str);
		JSONObject j = JSON.parseObject(resp);
		String data = j.getString("data");
		return data != null;
	}

	public static String getCodeParams(List<String> newCodes) throws Exception {
		StockApiReq req = new StockApiReq();
		req.setTrace(UUID.randomUUID().toString().replaceAll("-", ""));
		List<Map> symbol_list = new ArrayList<Map>();
		for (String code : newCodes) {
			Map map = new HashMap();
			map.put("code", code);
			symbol_list.add(map);
		}
		Map data = new HashMap();
		data.put("symbol_list", symbol_list);
		req.setData(data);
		String reqStr = JSON.toJSONString(req);
		String str = URLEncoder.encode(reqStr, "UTF-8");
		return str;
	}
}
