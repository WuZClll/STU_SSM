package com.itheima.a02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.annotation.Resource;
//BeanFactory(bean的容器)===》基础容器，底层容器
public class TestBeanFactory {

    public static void main(String[] args) {
        //bean工厂原始功能并不丰富， 扩展功能由后处理器来帮我们进行
        /**
         * DefaultListableBeanFactory=================默认单例的bean工厂容器
         */
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();//创建核心spring容器 刚创建好 内部没有任何bean
        //添加 bean 的定义 封装了bean的描述信息-》（class, scope, 初始化, 销毁）class:类型 scope:单例还是多例 在描述初始化方法和销毁方法等等
        /**
         * AbstractBeanDefinition==》抽象的bean定义
         * BeanDefinitionBuilder====》bean定义构建 返回值为↑
         * .genericBeanDefinition(Config.class)==》通用bean定义(bean的类型=》自己创造的配置类，其内有@bean)
         * .setScope("singleton")==》设置范围作用域（单例还是多例等等）
         * .getBeanDefinition()==》构建bean 返回抽象的bean定义
         */
        AbstractBeanDefinition beanDefinition =
                BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        /**
         * bean工厂.注册bean定义（"定义bean的名字"， bean的定义）
         * 注册完后 bean工厂里就有一个 beanDefinition bean定义的 名字为 config 的bean
         * beanFactory不能解析注解 比如Config类中的@Bean  @Configuration等
         */
        beanFactory.registerBeanDefinition("config", beanDefinition);


        // 给 BeanFactory 添加一些常用的后处理器 同时他也给我们设置了比较器
        /**
         * 注解配置工具.注册注解配置处理器（bean工厂）
         * 注册后内置的bean（bean工厂后处理器）可以被bean工厂管理到//此时后处理器还未被执行 遍历bean时无法遍历到Bean1Bean2等 只有config 和 内置bean后处理器
         */
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);

        // BeanFactory 后处理器主要功能，补充了一些 bean 定义 执行bean工厂后处理器
        /**
         * bean工厂.获取bean用类型获取（bean工厂后处理器.class）==》将beanFactory后处理器添加到beanFactory   获取到返回的是map集合
         * .值（）.开启流.遍历（遍历每一个bean工厂后处理器->{
         *      bean工厂后处理器.后处理器bean工厂（bean工厂）;//执行bean工厂后处理器  可以解析注解 比如Config类中的@Bean  @Configuration等 此时还没有自动注入@Autowired等的功能
         */
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });


        // Bean 后处理器, 针对 bean 的生命周期的各个阶段提供扩展, 例如 @Autowired @Resource ...
        /**
         * bean工厂.获取bean用类型获取（bean后处理器.class）==》将bean后处理器添加到beanFactory   获取到返回的是map集合
         * .值（）.开启流.排序（bean工厂.获取依赖比较器）==》改变bean后处理器的执行顺序
         *      .遍历（遍历每一个bean后处理器->{
         *      bean工厂.添加bean后处理器（bean后处理器）;//执行bean后处理器  可以解析注解例如 @Autowired @Resource ...
         */
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().stream()
                .sorted(beanFactory.getDependencyComparator())
                .forEach(beanPostProcessor -> {
            System.out.println(">>>>" + beanPostProcessor);//@Autowired @Resource 哪个先输出就代表哪个先注册进beaFactory 谁的优先级就高
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        });


        //遍历bean工厂容器里管理的bean名字
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }


        /**
         *bean工厂刚开始仅仅存了bean的描述信息（bean的名字等等），用到bean时才会初始化 调用构造方法 默认延时初始化
         * bean工厂.准备实例单例==》提前初始化单例bean 执行所有单例bean的构造方法
         */
        beanFactory.preInstantiateSingletons(); //初始化单例 准备好所有单例 执行所有单例bean的构造方法
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        System.out.println(beanFactory.getBean(Bean1.class).getBean2());//如果没有提前初始化 会在此时初始化bean1bean2 执行构造方法 并输出getBean2方法的地址
        System.out.println(beanFactory.getBean(Bean1.class).getInter());

        /**
            学到了什么:
            a. beanFactory 不会主动做的事
                   1. 不会主动调用 BeanFactory 后处理器==》可以解析注解 比如Config类中的@Bean  @Configuration等
                   2. 不会主动添加 Bean 后处理器==》可以解析注解例如 @Autowired @Resource ...
                   3. 不会主动初始化单例==》默认在第一次用到bean时才被初始化   beanFactory.preInstantiateSingletons(); //初始化单例 准备好所有单例 执行所有单例bean的构造方法 直接初始化
                   4. 不会解析beanFactory 还不会解析 ${ } 与 #{ }
            applicationContext会主动帮我们做这些事↑↑↑↑↑↑

            b. bean 后处理器会有排序的逻辑
         */


        //优先级数字越小优先级越高
        //Common的优先级为最大优先级-3
        //AutoWired的优先级为最大优先级-2
        System.out.println("==》排序的优先级（优先级数字越大优先级越小）比较器按数字从小到大排序 ：");
        System.out.println("==》Common:" + (Ordered.LOWEST_PRECEDENCE - 3));//最低优先级也就是最大数字-3 此值LOWEST_PRECEDENCE - 3时源码中的
        System.out.println("==》Autowired:" + (Ordered.LOWEST_PRECEDENCE - 2));//最低优先级也就是最大数字-2

    }

    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }

        @Bean
        public Bean3 bean3() {
            return new Bean3();
        }

        @Bean
        public Bean4 bean4() {
            return new Bean4();
        }
    }

    interface Inter {

    }

    static class Bean3 implements Inter {

    }

    static class Bean4 implements Inter {

    }

    static class Bean1 {
        private static final Logger log = LoggerFactory.getLogger(Bean1.class);

        public Bean1() {
            log.debug("构造 Bean1()");
        }

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2() {
            return bean2;
        }

        //两个注解都用时 哪个先注册进beaFactory 谁的优先级就高 参考：第72行
        @Autowired//自动注入的是bean3 有多个时按名字 =》bean3->Bean3
        @Resource(name = "bean4")//获取资源bean4 此时效果等同于自动注入   @Bean    public Bean4 bean4() {
        private Inter bean3;

        public Inter getInter() {
            return bean3;
        }
    }

    static class Bean2 {
        private static final Logger log = LoggerFactory.getLogger(Bean2.class);

        public Bean2() {
            log.debug("构造 Bean2()");
        }
    }
}
