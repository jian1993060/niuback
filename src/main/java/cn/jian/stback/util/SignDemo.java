package cn.jian.stback.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 签名demo
 */
public class SignDemo {

	public static void main(String[] args) throws IllegalAccessException {
//		ChargeBO bo = new ChargeBO(1, "1698787280173051905", null, System.currentTimeMillis() + "", "bsc",
//				"12312411665546", "http://127.0.0.1:10051/notify/test");
//		String s = signMap(SignUtil.objectToMap(bo), "cHds9god2aBDj5QrCu8j7f2qZ5V6EjPW");
//		System.out.println(s);
//		bo.setSign(s);
//		System.out.println(JSON.toJSONString(bo));
//		OkHttpUtil.sendPost("http://127.0.0.1:10051/api/pay/recharge", JSON.toJSONString(bo));
//		
//		
////		QueryBO bo = new QueryBO("1701267633620652034", null,"123124124",System.currentTimeMillis()+"");
////		String s = signMap(SignUtil.objectToMap(bo), "lYQySfIAhnjx3lJttCgCFDFoVTjrwmpG");
////		System.out.println(s);
////		bo.setSign(s);
////		System.out.println(JSON.toJSONString(bo));
//////		OkHttpUtil.sendPost("http://193.105.245.249/api/query", JSON.toJSONString(bo));
	}

	/**
	 * @param params 签名参数
	 * @param key    签名密钥
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String signMap(Map<String, String> params, String key) {
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
		System.out.println(text + "&key=" + key);
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
