package com.ca;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@EnableCaching //启动缓存配置(由springboot帮你完成自动配置)
@SpringBootApplication
@MapperScan("com.ca.mapper")
public class CleanairManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CleanairManageApplication.class, args);
    }

}
