package com.itheima.controller;

import com.itheima.doman.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    @Value("${lesson}")
    private String lesson;

    @Value("${server.port}")
    private Integer port;

    @Value("${enterprise.subject[0]}")
    private String subject_00;

//    使用独立对象把配置的所有变量都加载进去
    @Autowired
    private Environment environment;

//    新建实体类Enterprise，将实体类定义为bean自动装配，并配置配置配置属性
//    @Component
//    @ConfigurationProperties(prefix = "enterprise")/*enterprise: 指的是application.yaml中的enterprise*/
    @Autowired
    private Enterprise enterprise;

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id) {
        System.out.println(lesson);
        System.out.println(port);
        System.out.println(subject_00);
        System.out.println("-------------");
        System.out.println(environment.getProperty("lesson"));
        System.out.println(environment.getProperty("server.port"));
        System.out.println(environment.getProperty("enterprise.age"));
        System.out.println(environment.getProperty("enterprise.subject[1]"));
        System.out.println("--------------");
        System.out.println(enterprise);
        return "hello, springboot";
    }
}
