package com.ca;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ca.mapper")
public class CleanairCartApplication {

    public static void main(String[] args) {

        SpringApplication.run( CleanairCartApplication.class, args);
    }

}
