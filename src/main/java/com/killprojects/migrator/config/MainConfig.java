package com.killprojects.migrator.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.killprojects.migrator")
public class MainConfig {

	@Bean
	public JavaSparkContext sparkContext() {
		SparkConf conf = new SparkConf();
		conf.setAppName("data_migrator");
		conf.setMaster("local[1]");
		return new JavaSparkContext(conf);
	}

}
