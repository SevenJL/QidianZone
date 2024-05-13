package com.zone.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zone.entity.User;

/**
 * 用户服务
 */
public interface UserService extends IService<User> {

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


    /**
     * 退出
     * @param currentId 当前用户id
     */
    void logout(Integer currentId);


    /**
     * 根据id删除用户
     * @param id 用户id
     * @return
     */
    Boolean deleteById(Integer id ,Integer status);
}
