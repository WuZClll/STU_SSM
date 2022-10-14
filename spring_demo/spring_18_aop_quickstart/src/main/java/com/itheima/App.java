package com.itheima;

import com.itheima.config.SpringConfig;
import com.itheima.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookDao bookDao = ctx.getBean(BookDao.class);
        bookDao.update();
        System.out.println("---------");
        System.out.println(bookDao);//com.itheima.dao.impl.BookDaoImpl@14bdbc74
        System.out.println(bookDao.getClass());//class com.sun.proxy.$Proxy20
    }
}
