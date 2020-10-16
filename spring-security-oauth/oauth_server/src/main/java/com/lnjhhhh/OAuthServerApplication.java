package com.lnjhhhh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author lnjhhhh
 * @CreateDate 2020/10/2 20:50
 * @Version 1.0
 */

@SpringBootApplication
@MapperScan("com.lnjhhhh.mapper")
public class OAuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAuthServerApplication.class, args);
    }
}
