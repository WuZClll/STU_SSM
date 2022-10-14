package com.itheima.service.impl;

import com.itheima.service.BookService;
import org.springframework.stereotype.Service;

@Service//定义为bean
public class BookServiceImp implements BookService {
    @Override
    public void save() {
        System.out.println("book service is running...");
    }
}
