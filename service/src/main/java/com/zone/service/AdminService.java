package com.zone.service;

import com.zone.entity.User;

import java.util.List;

/**
 * 管理服务
 */
public interface AdminService {


    /**
     * 查询所有用户
     */
    List<User> listAllUsers();
}
