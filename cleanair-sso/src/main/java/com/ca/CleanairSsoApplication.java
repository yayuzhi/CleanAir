package com.ca;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.ca.mapper")
public class CleanairSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CleanairSsoApplication.class, args);
    }

}
