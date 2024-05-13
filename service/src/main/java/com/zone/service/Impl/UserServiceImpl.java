package com.zone.service.Impl;

import com.zone.dto.LoginDTO;
import com.zone.service.UserService;
import com.zone.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;

    @Override
    public Integer register(String password, String name, String email) {
        // 先判断 数据库中是否含有同名的用户
        if (userMapper.findByUserName(name) != null ) {
            // 存在同名用户 注册失败
            // 返回-1 表示注册失败
            return -1;
        }
        // 说明没有同名的用户
        // 返回生成的ID
        return userMapper.insert(password,name,email);
    }


    @Override
    public Integer login(LoginDTO loginDTO) {

        // 登录
        Integer userId = userMapper.login(loginDTO.getPassword(), loginDTO.getName());

        // 如果userid为空 则登录失败
        if (userId == null) {
            // 登录失败
            // 返回-1 表示登录失败
            log.info("用户:{}登录失败", loginDTO.getName());
            return -1;
        }

        return userId;
    }

}
