package com.itheima.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface LogService {
    //propagation设置事务属性：传播行为设置为当前操作需要新事务
    @Transactional(propagation = Propagation.REQUIRES_NEW)//propagation = Propagation.REQUIRES_NEW开启一个新事物，独立于事务管理员之外，
    // ，不受事务管理员管理的事务，原来添加日志和转入转出玛尼都受到事务管理员管理，加入到了同一个事物，现在它独立于此事务之外
    // ，你们玩你们的我玩我的，即使转入或转出失败我也记录日志，不参与转入转出事物的回滚
//    @Transactional//开启事务
    void log(String out, String in, Double money);
}
