package com.zone.mapper;


import com.zone.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface AdminMapper  {


    @Select("SELECT * FROM user")
    // 返回结果为一个所有用户的List集合
    List<User> listAllUsers();

}
