package cn.jian.stback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import cn.jian.stback.common.R;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

@RequestMapping("file")
@RestController
public class FileController {

	@Value("${file.url}")
	private String url;

	@Autowired
	MinioClient minioClient;

	@RequestMapping("upload")
	public R upload(@RequestParam("file") MultipartFile file) throws Exception {
		String fileName = IdWorker.get32UUID() + "." + file.getOriginalFilename().split("\\.")[1];
		minioClient.putObject(PutObjectArgs.builder().bucket("short").object("logo/"  + fileName)
				.stream(file.getInputStream(), -1, 10485760).contentType(file.getContentType()).build());
		return R.success(url + fileName);
	}

	public static void main(String[] args) {
		System.out.println("a.png".split("\\.").length);
	}
}
