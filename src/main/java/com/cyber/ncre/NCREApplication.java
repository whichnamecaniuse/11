package com.cyber.ncre;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.cyber.ncre.mapper") // 指定 MyBatis Mapper 接口扫描路径

public class NCREApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NCREApplication.class, args);
    }
}