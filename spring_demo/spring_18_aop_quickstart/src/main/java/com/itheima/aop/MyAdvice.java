package com.itheima.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
//通知类必须配置成Spring管理的bean
@Component
//设置当前类为切面类(aop)类
@Aspect
public class MyAdvice {
    //设置切入点，要求配置在方法上方      返回值是void的 ...的...方法
    //       1. Spring容器启动
    //       2．读取所有切面配置中的切入点
    //       3．初始化bean，判定bean对应的类中的方法是否匹配到任意切入点  是指  @Pointcut("execution(void com.itheima.dao.BookDao.update())")  中是否匹配到此方法
    //              匹配失败,创建对象
    //              匹配成功，创建原始对象（目标对象）的代理对象
    //      4．获取bean执行打法
    //          获取bean调用方法并执行，完成操作
    //          获取的bean是代理对象时，根据代理对象的运行模式运行原始方法与增强的内容，完成操作

    @Pointcut("execution(void com.itheima.dao.BookDao.update())")
    private void pt(){}

    //设置在切入点pt()的前面运行当前操作（前置通知）共性功能
     @Before("pt()")//在method方法前执行pt()
    public void method(){
        System.out.println(System.currentTimeMillis());
    }
}
