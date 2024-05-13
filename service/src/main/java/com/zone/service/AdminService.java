package com.zone.service;

import com.zone.dto.LoginDTO;

import java.util.List;

/**
 * 用户服务
 */
public interface AdminService {


    /**
     * 登录
     * @param password
     * @param name
     * @return
     */
    Integer login(LoginDTO loginDTO);
}
