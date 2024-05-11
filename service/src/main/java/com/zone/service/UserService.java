package com.zone.service;

/**
 * 用户服务
 */
public interface UserService {

    /**
     * 注册
     * @param password 密码
     * @param userName 用户名
     * @param email    用户邮箱
     * @return
     */
    Integer register(String password, String userName, String email);

    /**
     * 登录
     * @param password 密码
     * @param userName 用户名
     */
    Integer login(String password, String userName);


    //登录

    //退出
}
