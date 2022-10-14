package com.itheima.a01_BeanFactory_ApplicationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
/**
学到了什么
     BeanFactory 能干点啥
         - 表面上只有 getBean
         - 实际上控制反转、基本的依赖注入、直至 Bean 的生命周期的各种功能, 都由它的实现类提供
     a. BeanFactory 与 ApplicationContext 并不仅仅是简单接口继承的关系,
       ,ApplicationContext 组合并扩展了 BeanFactory 的功能
          拓展了：
             MessageSource接口 具备翻译的能力 处理国际化资源ApplicationContext实现的接口之一
             ResourcePatternResolver接口 具备通配符匹配资源的能力  ApplicationContext实现的接口之一
             EnvironmentCapable接口 读取一些环境信息 返回系统配置的路径 ApplicationContext实现的接口之一
             ApplicationEventPublisher接口 用来发布事件对象 ApplicationContext实现的接口之一
                 发布事件最大的作用是实现组件中的解耦

 b. 又新学一种代码之间解耦途径  （事件）

/*
    BeanFactory 与 ApplicationContext 的区别
 */
//ctrl+alt+u弹出java类图
@SpringBootApplication
public class A01 {

    private static final Logger log = LoggerFactory.getLogger(A01.class);

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {

        ConfigurableApplicationContext context = SpringApplication.run(A01.class, args);
        /**
         1. 到底什么是 BeanFactory
         - 它是 ApplicationContext 的父接口
         - 它(BeanFactory)才是 Spring 的核心容器, 主要的 ApplicationContext 实现都【组合】了它的功能
         */
//        context.getBean("aaa");

/*源码
        public Object getBean (String name) throws BeansException {
            this.assertBeanFactoryActive();
            return this.getBeanFactory().getBean(name);
        }
*/

        System.out.println(context);
//此处断点调试后发现 contect容器==下==》beanFactory==下==》singletonObject单例对象存在这里

        /**
         2. BeanFactory 能干点啥
         - 表面上只有 getBean
         - 实际上控制反转、基本的依赖注入、直至 Bean 的生命周期的各种功能, 都由它的实现类提供
         */
        //反射的方式获取DefaultSingletonBeanRegistry（默认单例bean注册）类中的singletonObjects（单例对象）变量
        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");//获取声明的变量singletonObjects：单例对象
        singletonObjects.setAccessible(true);//因为他（变量）是私有的setAccessible设置可到达的（true）
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String, Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);
        //对结果进行过滤
        // entrySet()==》拿到键值的集合====》filter对结果进行过滤====》startsWith("component")找到component开头的
        map.entrySet().stream().filter(e -> e.getKey().startsWith("component"))
                .forEach(e -> {
                    System.out.println(e.getKey() + "=" + e.getValue());//component1=com.itheima.a01.Component1@41bfa9e9
                    //component2=com.itheima.a01.Component2@68b7d0ef
                });

        /**
         3. ApplicationContext 比 BeanFactory 多点啥
         MessageSource接口 具备翻译的能力 处理国际化资源ApplicationContext实现的接口之一
         ResourcePatternResolver接口 具备通配符匹配资源的能力  ApplicationContext实现的接口之一
         EnvironmentCapable接口 读取一些环境信息 返回系统配置的路径 ApplicationContext实现的接口之一
         ApplicationEventPublisher接口 用来发布事件对象 ApplicationContext实现的接口之一
                发布事件最大的作用是实现组件中的解耦

         */
        System.out.println("=======ApplicationContext 比 BeanFactory 多点啥\n" +
                "-----------MessageSource接口 具备翻译的能力 处理国际化资源ApplicationContext实现的接口之一");
        //MessageSource接口 具备翻译的能力 处理国际化资源ApplicationContext实现的接口之一 应用自己的配置文件src/main/resources/messages.properties（存储翻译对应文字的键值对【有的乱码了】）
        System.out.println(context.getMessage("hi", null, Locale.CHINA));
        System.out.println(context.getMessage("hi", null, Locale.ENGLISH));
        System.out.println(context.getMessage("hi", null, Locale.JAPANESE));

        System.out.println("----------ResourcePatternResolver接口 具备通配符匹配资源的能力  ApplicationContext实现的接口之一");
        //ResourcePatternResolver接口 具备通配符匹配资源的能力  ApplicationContext实现的接口之一
        //  classpath:在class路径下找； classpath*:可以在jar包中找
        //  返回文件真实路径
        Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");//jar包中的文件
        for (Resource resource : resources) {
            System.out.println(resource);
        }

        System.out.println("----------EnvironmentCapable接口 读取一些环境信息 返回系统配置的路径 ApplicationContext实现的接口之一");
        //EnvironmentCapable接口 读取一些环境信息 返回系统配置的路径 ApplicationContext实现的接口之一
        System.out.println("java_home: " + context.getEnvironment().getProperty("java_home"));
        System.out.println("server.port: " + context.getEnvironment().getProperty("server.port"));//来自application.properties文件

        System.out.println("----------ApplicationEventPublisher接口 用来发布事件对象 ApplicationContext实现的接口之一"
        + "\n     ~~~~发布事件最大的作用是实现组件中的解耦");
        //ApplicationEventPublisher接口 用来发布事件对象 ApplicationContext实现的接口之一
            //自己写的用户已注册事件类 继承ApplicationEvent类
            // public class UserRegisteredEvent extends ApplicationEvent {
            //    //Object source事件源 就是谁发的事件
            //    public UserRegisteredEvent(Object source) {
            //        super(source);
            //    }
            //}
        //发事件
//        context.publishEvent(new UserRegisteredEvent(context));//事件源（谁发的事件）context
        context.getBean(Component1.class).register();
        //收事件
        //class Component2{
        //    @EventListener//作为事件监听器 发的是什么事件 用什么事件来收
        //    public void aaa(UserRegisteredEvent event) {

        /**
            4. 学到了什么
                a. BeanFactory 与 ApplicationContext 并不仅仅是简单接口继承的关系, ApplicationContext 组合并扩展了 BeanFactory 的功能
                b. 又新学一种代码之间解耦途径  （事件）
            练习：完成用户注册与发送短信之间的解耦, 用事件方式、和 AOP 方式分别实现
         */
    }
}
