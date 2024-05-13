package com.zone.service;


import com.zone.dto.LoginDTO;
import com.zone.dto.UserUpdatePasswordDTO;

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
    Integer register(String password, String name, String email);

    /**
     * 登录
     * @param loginDTO 登录信息
     */
    Integer login(LoginDTO loginDTO);

    /**
     * 修改密码
     * @param userUpdatePasswordDTO 修改密码信息
     * @return
     */
    Integer update(UserUpdatePasswordDTO userUpdatePasswordDTO);
}
