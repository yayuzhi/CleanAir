package com.ca;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("com.ca.mapper")
public class CleanairManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CleanairManageApplication.class, args);
    }

}
