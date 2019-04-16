package com.global;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.global.api.dao*")
@SpringBootApplication
@ComponentScan(basePackages={"com"})
public class GlobalproApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobalproApplication.class, args);
	}

}
