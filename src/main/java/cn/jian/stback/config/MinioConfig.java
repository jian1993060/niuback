package cn.jian.stback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
public class MinioConfig {

	@Bean
	public MinioClient init() {
		MinioClient minioClient = MinioClient.builder().endpoint("http://156.245.12.89:9000")
				.credentials("minioadmin", "Jian1993").build();
		return minioClient;
	}
}
