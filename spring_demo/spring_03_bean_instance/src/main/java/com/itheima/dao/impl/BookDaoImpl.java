package com.itheima.dao.impl;

import com.itheima.dao.BookDao;

public class BookDaoImpl implements BookDao {
//    私有的也能访问 √
//    private BookDaoImpl() {//√
//        System.out.println("book dao constructor is running ....");
//    }

//    错×
//    public BookDaoImpl(int i) {//×
//        System.out.println("book dao constructor is running ....");
//    }

    //    spring创建bean时调用的是无参构造方法
    public BookDaoImpl() {
        System.out.println("book dao constructor is running ....");
    }

    public void save() {
        System.out.println("book dao save ...");
    }

}
