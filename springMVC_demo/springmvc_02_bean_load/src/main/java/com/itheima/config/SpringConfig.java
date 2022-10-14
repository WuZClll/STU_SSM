package com.itheima.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

@Configuration
//@ComponentScan({"com.itheima.service","com.itheima.dao"})
//设置spring配置类加载bean时的过滤规则，当前要求排除掉表现层springMVC(controller包下)对应的bean
//excludeFilters属性：设置扫描加载bean时，排除的过滤规则
//type属性：设置排除规则，当前使用按照bean定义时的注解类型进行排除
//classes属性：设置排除的具体注解类，当前设置排除@Controller定义的bean
@ComponentScan(value="com.itheima",
    excludeFilters = @ComponentScan.Filter(/*排除扫描*/
        type = FilterType.ANNOTATION,/*按注解过滤*/
        classes = Controller.class/*过滤掉有@Controller注解的类*/
    )
)
public class SpringConfig {
}
