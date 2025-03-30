package cn.jian.stback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		//config.setAllowCredentials(true);
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
		configSource.registerCorsConfiguration("/**", config);
		System.out.println(1);
		return new CorsFilter(configSource);
	}
}
