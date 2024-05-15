package com.zone.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zone.dto.LoginDTO;
import com.zone.dto.UserUpdatePasswordDTO;
import com.zone.entity.User;

/**
 * 用户服务
 */
public interface UserService extends IService<User> {

    /**
     * 注册
     * @param password 密码
     * @param name 用户名
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

    /**
     * 修改昵称
     * @param userId   用户id
     * @param nickname 昵称
     */
    void updateNickname(Integer userId, String nickname);

    /**
     * 修改头像
     */
    void updateAvatar(Integer userId, String avatarUrl);

    /**
     * 获取用户信息
     */
    User getUserInfo(Integer userId);
}
