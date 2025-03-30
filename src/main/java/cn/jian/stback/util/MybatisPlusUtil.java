package cn.jian.stback.util;

import java.util.Collections;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MybatisPlusUtil {
	public static void main(String[] args) {
		run();
	}

	public static void run() {

		FastAutoGenerator.create("jdbc:mysql://156.245.12.89:3306/stock", "root", "Jian1993!")
				.globalConfig(builder -> builder.author("jian").outputDir("D:\\workspace\\niuback" + "/src/main/java")
						.commentDate("yyyy-MM-dd"))
				.packageConfig(builder -> builder.parent("cn.jian.stback").entity("entity").mapper("mapper")
						.service("service").serviceImpl("service.impl")
						.pathInfo(Collections.singletonMap(OutputFile.xml,
								"D:\\workspace\\niuback" + "/src/main/resources/mapper")))
				.strategyConfig(builder -> builder.entityBuilder().enableLombok().versionColumnName("version").enableFileOverride()
						.controllerBuilder().disable().serviceBuilder().formatServiceFileName("%sService")
						.formatServiceImplFileName("%sServiceImpl"))
				.templateEngine(new FreemarkerTemplateEngine()).execute();

	}
}
