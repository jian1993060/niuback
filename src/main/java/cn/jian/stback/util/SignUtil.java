package cn.jian.stback.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SignUtil {

//	public static void main(String[] args) throws Exception {
////		ChargeBO bo = new ChargeBO("1", "1724770005190598658", null, System.currentTimeMillis() + "", "kuaishou",
////				"1231241111243", "https://hash-pay.org/api/pay/123");
////		String s = sign(bo, "JApfBuylrFMh6eGpdwgpgqXjGyC15nPT");
////		System.out.println(s);
////		bo.setSign(s);
////		System.out.println(JSON.toJSONString(bo));
////		OkHttpUtil.sendPost("https://hash-pay.org/api/pay/order", JSON.toJSONString(bo));
//		String a = URLEncoder.encode("https://ksurl.cn/MwHXopJA","utf-8");
//		System.out.println(a);
//	}

//	public static void main(String[] args) throws Exception {
//		String s =OkHttpUtil.qx("10");
//		
//		System.out.println(s);
//	}

	// java 对象转 map
	public static Map<String, String> objectToMap(Object obj) throws IllegalAccessException {
		if (obj == null)
			return null;
		Map<String, String> map = new HashMap<>();
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			Object fieObj = field.get(obj);
			if (fieObj != null) {
				if (StringUtils.isNoneBlank(fieObj + "")) {
					map.put(field.getName(), fieObj + "");
				}

			}
		}
		return map;
	}

	/**
	 * @param params
	 * @param key
	 * @return
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean checkParam(Object obj, String key) throws Exception {
		Map<String, String> params = objectToMap(obj);
		boolean result = false;
		if (params.containsKey("sign")) {
			String sign = params.get("sign");
			params.remove("sign");
			StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
			buildPayParams(buf, params);
			String preStr = buf.toString();
			String signRecieve = sign(preStr, key, "utf-8");
			result = sign.equalsIgnoreCase(signRecieve);
		}
		return result;
	}

	public static String sign(Object obj, String key) throws IllegalAccessException {
		Map<String, String> params = objectToMap(obj);
		StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
		buildPayParams(buf, params);
		String preStr = buf.toString();
		String signRecieve = sign(preStr, key, "utf-8");
		return signRecieve;
	}

	public static String signMap(Map<String, String> params, String key) throws IllegalAccessException {
		StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
		buildPayParams(buf, params);
		String preStr = buf.toString();
		String signRecieve = sign(preStr, key, "utf-8");
		return signRecieve;
	}

	/**
	 * @param payParams
	 * @return
	 * @author
	 */
	public static void buildPayParams(StringBuilder sb, Map<String, String> payParams) {
		List<String> keys = new ArrayList<String>(payParams.keySet());
		Collections.sort(keys);
		for (String key : keys) {
			if (StringUtils.isBlank(payParams.get(key))) {
				continue;
			}
			sb.append(key).append("=");
			sb.append(payParams.get(key));

			sb.append("&");
		}
		sb.setLength(sb.length() - 1);
	}

	/**
	 * 签名字符串
	 *
	 * @param text          需要签名的字符串
	 * @param key           密钥
	 * @param input_charset 编码格式
	 * @return 签名结果
	 */
	public static String sign(String text, String key, String input_charset) {
		log.info("签名字符串：" + text + "&key=" + key);
		return DigestUtils.md5Hex(getContentBytes(text + "&key=" + key, input_charset)).toUpperCase();
	}

	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}

}
