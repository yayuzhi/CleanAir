package com.ca;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ca.mapper") //为mapper接口创建代理对象
public class CleanairOrderApplication {
	
	
	public static void main(String[] args) {
		
		SpringApplication.run(CleanairOrderApplication.class, args);
	}

}