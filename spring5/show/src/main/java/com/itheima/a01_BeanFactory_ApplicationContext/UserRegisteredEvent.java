package com.itheima.a01_BeanFactory_ApplicationContext;

import org.springframework.context.ApplicationEvent;
//用户已注册事件
public class UserRegisteredEvent extends ApplicationEvent {
    //Object source事件源 就是谁发的事件
    public UserRegisteredEvent(Object source) {
        super(source);
    }
}
