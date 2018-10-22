package com.thanhpl.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
public class SampleQuartzApplication {
	public static void main(String[] args) {
		SpringApplication.run(SampleQuartzApplication.class, args);
	}
}
