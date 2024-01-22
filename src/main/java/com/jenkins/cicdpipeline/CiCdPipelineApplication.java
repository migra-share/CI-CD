package com.jenkins.cicdpipeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.jenkins")
public class CiCdPipelineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CiCdPipelineApplication.class, args);
	}

}
