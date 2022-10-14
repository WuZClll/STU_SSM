package com.itheima.service.impl;

import com.itheima.dao.AccountDao;
import com.itheima.service.AccountService;
import com.itheima.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private LogService logService;

    public void transfer(String out,String in ,Double money) throws IOException {
        try{
            accountDao.outMoney(out,money);
            int i = 1/0;
//            if (true)throw new IOException();//默认遇到此异常不回滚事务，设置@Transactional(rollbackFor={IOException.class})就回滚
            accountDao.inMoney(in,money);
        }finally {
            logService.log(out,in,money);//日志功能
        }
    }

}
