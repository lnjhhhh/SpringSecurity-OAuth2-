package com.lnjhhhh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lnjhhhh
 * @CreateDate 2020/10/2 19:22
 * @Version 1.0
 */

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/findAll")
    public String findAll(){
        return "获取到所有的产品信息";
    }

}
