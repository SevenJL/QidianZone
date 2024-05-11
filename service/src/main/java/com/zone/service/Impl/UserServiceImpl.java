package com.zone.service.Impl;

import com.zone.service.UserService;
import com.zone.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer register(String password, String userName, String email) {
        // 先判断 数据库中是否含有同名的用户
        if (userMapper.findByUserName(userName) != null ) {
            // 存在同名用户 注册失败
            // 返回-1 表示注册失败
            return -1;
        }
        // 说明没有同名的用户
        // 返回生成的ID
        return userMapper.insert(password,userName,email);
    }


    @Override
    public Integer login(String password, String userName) {
        // 登录
        Integer userId = userMapper.login(password,userName);

        // 如果userid为空 则登录失败
        if (userId == null) {
            // 登录失败
            // 返回-1 表示登录失败
            return -1;
        }

        return userId;

    }


}
