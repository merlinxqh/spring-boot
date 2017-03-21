package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 执行main方法 启动服务 或者 mvn spring-boot:run 启动服务
 * @author leo
 *
 */
@SpringBootApplication
//@RestController是一个特殊的@Controller,它的返回值可以直接作为http response的body部分直接返回给浏览器
@RestController
public class Application {
	
    @RequestMapping("/")
    public String greeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
