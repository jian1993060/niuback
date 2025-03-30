package cn.jian.stback.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.jian.stback.common.JwtUser;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 项目参数工具类
 */
public class ResourceUtil {

	public static final String LOCAL_CLINET_USER = "LOCAL_CLINET_USER";

	/**
	 * 国际化【缓存】
	 */
	public static Map<String, String> mutiLangMap = new HashMap<String, String>();

	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;

	}

	public static String getIp() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	public static JwtUser getCurrentUser() {
		HttpServletRequest request = getRequest();
		return (JwtUser) request.getAttribute("user");
	}

	public static String getLanguage() {
		HttpServletRequest request = getRequest();
		String lang = request.getHeader("language");
		return StringUtils.isBlank(lang) ? "en" : lang;
	}

	public static String getApiUrl() {
		HttpServletRequest request = getRequest();
		return (String) request.getAttribute("ApiUrl");
	}

	/**
	 * 获取session定义名称
	 *
	 * @return
	 */

	/**
	 * 获得请求路径
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI() + "?" + request.getQueryString();
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
		return requestPath;
	}

	public static String getSysPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
		String temp = path.replaceFirst("file:/", "").replaceFirst("WEB-INF/classes/", "");
		String separator = System.getProperty("file.separator");
		String resultPath = temp.replaceAll("/", separator + separator).replaceAll("%20", " ");
		return resultPath;
	}

	/**
	 * 获取项目根目录
	 *
	 * @return
	 */
	public static String getPorjectPath() {
		String nowpath; // 当前tomcat的bin目录的路径 如
		// D:\java\software\apache-tomcat-6.0.14\bin
		String tempdir;
		nowpath = System.getProperty("user.dir");
		tempdir = nowpath.replace("bin", "webapps"); // 把bin 文件夹变到 webapps文件里面
		tempdir += "\\"; // 拼成D:\java\software\apache-tomcat-6.0.14\webapps\sz_pro
		return tempdir;
	}

	public static String getClassPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
		String temp = path.replaceFirst("file:/", "");
		String separator = System.getProperty("file.separator");
		String resultPath = temp.replaceAll("/", separator + separator);
		return resultPath;
	}

	public static String getSystempPath() {
		return System.getProperty("java.io.tmpdir");
	}

	public static String getSeparator() {
		return System.getProperty("file.separator");
	}

	// ---author:jg_xugj----start-----date:20151226--------for：#814
	// 【数据权限】扩展支持写表达式，通过session取值
	/**
	 * 获取用户session 中的变量
	 *
	 * @param key Session 中的值
	 * @return
	 */

	/**
	 * 处理数据权限规则变量 以用户变量为准 先得到用户变量，如果用户没有设置，则获到 系统变量
	 *
	 * @param key Session 中的值
	 * @return
	 */

	public static Map<String, Object> setConditionMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (obj == null) {
			return null;
		}
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			if (getValueByFieldName(fieldName, obj) != null)
				map.put(fieldName, getValueByFieldName(fieldName, obj));
		}

		return map;

	}

	/**
	 * 根据属性名获取该类此属性的值
	 *
	 * @param fieldName
	 * @param object
	 * @return
	 */
	private static Object getValueByFieldName(String fieldName, Object object) {
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String getter = "get" + firstLetter + fieldName.substring(1);
		try {
			Method method = object.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(object, new Object[] {});
			return value;
		} catch (Exception e) {
			return null;
		}

	}

	// 比较两个数组的交际
	public static List<Integer> intersect(List<Integer> scids, List<Integer> roleids) {
		Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
		List<Integer> list = new ArrayList<Integer>();
		for (Integer str : scids) {
			if (!map.containsKey(str)) {
				map.put(Integer.valueOf(str), Boolean.FALSE);
			}
		}
		for (Integer str : roleids) {
			if (map.containsKey(str)) {
				map.put(str, Boolean.TRUE);
			}
		}

		for (Entry<Integer, Boolean> e : map.entrySet()) {
			if (e.getValue().equals(Boolean.TRUE)) {
				list.add(e.getKey());
			}
		}

		return list;
	}

	// 拼接查询
	public static String sql(String bz, List<Integer> menuids) {
		String a = bz + "=";
		for (int i = 0; i < menuids.size(); i++) {
			if (i == menuids.size() - 1) {
				a = a + menuids.get(i).toString();
			} else {
				a = a + menuids.get(i).toString() + " or " + bz + "=";
			}
		}
		return a;
	}

	// 拼接查询
	public static String sqlString(String bz, List<String> menuids) {
		String a = bz + "=";
		for (int i = 0; i < menuids.size(); i++) {
			if (i == menuids.size() - 1) {
				a = a + menuids.get(i);
			} else {
				a = a + menuids.get(i) + " or " + bz + "=";
			}
		}
		return a;
	}

	// 获取一个8位的时间戳
	public static String getDataRd() {
		String a = ((Long) System.currentTimeMillis()).toString();
		return a;

	}

	// public static boolean isFuzzySearch(){
	// return "1".equals(bundle.getString("fuzzySearch"));
	// }

}
