package com.awei.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: yeb
 * @author: Awei
 * @create: 2021-03-09 16:53
 **/
@SpringBootApplication
@MapperScan("com.awei.server.mapper")
@EnableScheduling
public class YebApplication {

    public static void main(String[] args) {
        SpringApplication.run(YebApplication.class, args);
    }
}
