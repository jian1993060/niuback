package cn.jian.stback.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.jian.stback.common.ZjException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public class OkHttpUtil {
	private final static OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

	static OkHttpClient okHttpClient;

	public static String sendPost(String url, String info) {
		Response response = null;
		try {
			MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
			RequestBody body = RequestBody.Companion.create(info, mediaType);
			Request request = new Request.Builder().url(url).post(body).build();
			response = okHttpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				String resp = response.body().string();
				log.info("返回" + resp);
				return resp;
			} else {
				throw new IOException("异常： " + response.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			if (response != null) {
				response.close();
			}
		}

	}
	
	public static String sendGet(String url) {
		Response response = null;
		try {
			Request request = new Request.Builder().url(url).get().build();
			response = okHttpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				String resp = response.body().string();
				return resp;
			} else {
				throw new ZjException("异常： " + response.toString());
			}
		} catch (Exception e) {
			return "";
		} finally {
			if (response != null) {
				response.close();
			}
		}

	}
	

	static {

//		okHttpClient = httpBuilder.connectTimeout(60000L, TimeUnit.MILLISECONDS)
//				.readTimeout(300000L, TimeUnit.MILLISECONDS)
//				.proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 10808)))
//				.writeTimeout(300000L, TimeUnit.MILLISECONDS).build();
//	}

		okHttpClient = httpBuilder.connectTimeout(60000L, TimeUnit.MILLISECONDS)
				.readTimeout(300000L, TimeUnit.MILLISECONDS).writeTimeout(300000L, TimeUnit.MILLISECONDS).build();
	}
}
