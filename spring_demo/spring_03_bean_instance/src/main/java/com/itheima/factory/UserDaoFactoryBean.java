package com.itheima.factory;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import org.springframework.beans.factory.FactoryBean;
//FactoryBean创建对象
//想用他造什么对象，泛型就写什么
public class UserDaoFactoryBean implements FactoryBean<UserDao> {
    //代替原始实例工厂中创建对象的方法
    public UserDao getObject() throws Exception {
        return new UserDaoImpl();
    }

    //创建的对象是什么类型的
    public Class<?> getObjectType() {
        return UserDao.class;
    }

//    //通常我们都是单例省略此方法
//    @Override
//    public boolean isSingleton() {
//        return true;/*代表创建出来的对象是单例的，默认为单例（true）*/
//    }
}
