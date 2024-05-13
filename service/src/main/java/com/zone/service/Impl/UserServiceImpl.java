package com.zone.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zone.constant.PowerConstant;
import com.zone.constant.StatusConstant;
import com.zone.entity.User;
import com.zone.service.UserService;
import com.zone.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer register(String password, String name, String email) {
        // 先判断 数据库中是否含有同名的用户
        if (userMapper.findByUserName(name) != null ) {
            // 存在同名用户 注册失败
            // 返回-1 表示注册失败
            return -1;
        }
        if (userMapper.findUserByEmail(email) != null) {
            // 存在同名邮箱 注册失败
            // 返回-1 表示注册失败
            return -1;
        }


        //TODO 根据用户输入的密码 进行MD5加密 再存储到数据困中 避免密码泄露

        // 说明没有同名的用户
        // 更新信息
        LocalDateTime updateTime = LocalDateTime.now(); // 更新时间
        LocalDateTime createTime = LocalDateTime.now(); // 创建时间
        Integer status = StatusConstant.ENABLE; // 状态

        //注册用户 默认为user 管理员只能由当前的管理者进行更改power
        String power = PowerConstant.DEFAULT_POWER; // user权限

        // 返回生成的ID
        return userMapper.insert(password,name,email,updateTime,createTime,status,power);
    }


    @Override
    public Integer login(String password, String userName) {
        // 登录
        Integer userId = userMapper.login(password,userName);

        // TODO 使用jwt令牌进行验证用户身份

        // 如果userid为空 则登录失败
        if (userId == null) {
            // 登录失败
            // 返回-1 表示登录失败
            return -1;
        }

        return userId;

    }



    @Override
    public void logout(Integer currentId) {

        // 更新用户状态
        Integer status = StatusConstant.DISABLE;
        userMapper.logout(currentId,status);
        log.info("用户退出成功");
    }




}
