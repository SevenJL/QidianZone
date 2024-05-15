package com.zone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zone.dto.LoginDTO;
import com.zone.entity.Admin;

import java.util.List;

/**
 * 用户服务
 */
public interface AdminService  {


    /**
     * 登录
     * @return
     */
    Integer login(LoginDTO loginDTO);
}
