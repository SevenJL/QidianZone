package com.zone.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zone.dto.LoginDTO;
import com.zone.entity.User;

/**
 * 用户服务
 */

public interface UserService extends IService<User> {

    /**
     * 注册
     */
    Integer register(String password, String account, String email);

    /**
     * 登录
     */
    Integer login(LoginDTO loginDTO);

    /**
     * 修改昵称
     */
    void updateNickName( String nickName);

    /**
     * 修改头像
     */
    void updateAvatar(String avatarUrl);

    /**
     * 修改密码
     */
    void updatePassword(String password);

    /**
     * 获取用户信息
     */
    User getUserInfo(Integer id);


}
