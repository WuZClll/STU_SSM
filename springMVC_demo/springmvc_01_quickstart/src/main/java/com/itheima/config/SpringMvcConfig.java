package com.itheima.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//springmvc配置类，本质上还是一个spring配置类
@Configuration//做一个配置类
@ComponentScan("com.itheima.controller")
public class SpringMvcConfig {
}
