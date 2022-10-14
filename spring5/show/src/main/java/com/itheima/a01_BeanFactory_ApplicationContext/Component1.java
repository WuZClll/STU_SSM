package com.itheima.a01_BeanFactory_ApplicationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Component1 {

    private static final Logger log = LoggerFactory.getLogger(Component1.class);

    @Autowired
    private ApplicationEventPublisher aepcontext;//ApplicationContext的父接口 具备事件的发布的功能
//    private ApplicationContext applicationContext;//也可以

    public void register() {
        log.debug("用户注册");
        aepcontext.publishEvent(new UserRegisteredEvent(this));//发送事件 2接收
    }

}
