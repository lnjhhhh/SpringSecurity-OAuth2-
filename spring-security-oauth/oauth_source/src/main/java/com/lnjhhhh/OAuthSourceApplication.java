package com.lnjhhhh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author lnjhhhh
 * @CreateDate 2020/10/2 19:14
 * @Version 1.0
 */

@SpringBootApplication
@MapperScan("com.lnjhhhh.mapper")
public class OAuthSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthSourceApplication.class, args);
    }

}
