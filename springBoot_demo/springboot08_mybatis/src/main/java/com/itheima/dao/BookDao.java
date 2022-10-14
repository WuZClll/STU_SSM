package com.itheima.dao;

import com.itheima.doman.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper//原来的在config类中扫描了此类，现在没有config类，就要加注解
public interface BookDao {
    @Select("select * from tbl_book where id = #{id}")
    public Book getById(Integer id);
}
