package cn.jian.stback.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import cn.jian.stback.common.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

//前后端分离token验证，跨域外网可访问
public class TokenUtil {

	// 自定义验证key
	private final static String API_KEY = "FGRT3456gbnh1357fgrwasfgsg34fgnhjuyrdvvf456u6jdjfdg";

	// 过期时间
	private final static long ttlMillis = 360000 * 1000;

	public static String createJWT(JwtUser user) {
		SecretKey key = generalKey();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", user.getId());
		map.put("userType", user.getUserType());
		JwtBuilder builder = Jwts.builder().addClaims(map).signWith(key, SignatureAlgorithm.HS256);
		if (ttlMillis >= 0) {
			long expMillis = System.currentTimeMillis() + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		String token = builder.compact();
		user.setToken(token);
		return token;
	}

	/**
	 * 由字符串生成加密key
	 *
	 * @return
	 */
	public static SecretKey generalKey() {
		return Keys.hmacShaKeyFor(API_KEY.getBytes());
	}

	public static JwtUser parseJWT(String jwt) {
		JwtUser jwtUser = new JwtUser();
		try {
			SecretKey key = generalKey();
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
			jwtUser.setId(claims.get("id") + "");
			jwtUser.setToken(jwt);
			jwtUser.setUserType(claims.get("userType") == null ? null : claims.get("userType") + "");
			jwtUser.setCode(true);
			return jwtUser;
		} catch (Exception e) {
			e.printStackTrace();
			jwtUser.setCode(false);
			return jwtUser;
		}

	}

}