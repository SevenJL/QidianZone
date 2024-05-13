package com.zone.service;


import com.zone.dto.LoginDTO;

/**
 * 用户服务
 */
public interface UserService {

    /**
     * 注册
     * @param password 密码
     * @param userName 用户名
     * @param email    用户邮箱
     */
    Integer register(String password, String userName, String email);

    /**
     * 登录
     * @param loginDTO 登录信息
     */
    Integer login(LoginDTO loginDTO);



}
