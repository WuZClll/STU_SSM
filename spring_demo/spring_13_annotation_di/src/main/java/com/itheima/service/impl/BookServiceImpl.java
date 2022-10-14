package com.itheima.service.impl;

import com.itheima.dao.BookDao;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    //@Autowired：注入引用类型，自动装配模式，默认按类型装配
    @Autowired//自动装配 没有定义bean名字时按类型装配 且用暴力反射，可以省略set; 如果有相同的类型最好用@Qualifier按名称装配
    //@Qualifier：自动装配bean时按bean名称装配
    @Qualifier("bookDao")//此注解依赖@Autowired
    private BookDao bookDao;

    public void save() {
        System.out.println("book service save ...");
        bookDao.save();
    }
}
