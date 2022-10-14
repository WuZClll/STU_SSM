package com.itheima;

import com.itheima.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        //1.加载类路径下的配置文件
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");//不延迟加载，但我们在applicationContext.xml中配置了延迟加载，不配置延迟加载时会自动执行BookDaoImpl的无参构造方法输出constructor
        //2.从文件系统下加载配置文件
//        ApplicationContext ctx = new FileSystemXmlApplicationContext("C:\\Users\\^\\Documents\\Tencent Files\\1302344595\\FileRecv\\spring_demo\\spring_10_container\\src\\main\\resources\\applicationContext.xml");

//        BookDao bookDao = ctx.getBean("bookDao",BookDao.class);
//        BookDao bookDao = ctx.getBean(BookDao.class);//前提：容器中只能有一个bean 按类型分配 自动装配类型
//        bookDao.save();
    }
}
