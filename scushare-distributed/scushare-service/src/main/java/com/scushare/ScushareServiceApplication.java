package com.scushare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations= {"classpath:/dubbo-service.xml"})
public class ScushareServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScushareServiceApplication.class, args);
	}
}
