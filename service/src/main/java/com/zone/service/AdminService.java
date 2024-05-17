package com.zone.service;

import com.zone.dto.AdminDTO;
import com.zone.dto.LoginDTO;

/**
 * 用户服务
 */
public interface AdminService  {


    /**
     * 登录
     */
    Integer login(LoginDTO loginDTO);


    /**
     * 删除用户
     */
    void deleteById(String id);

    /**
     * 修改管理员账号/密码
     */
    void updateAdmin(AdminDTO adminDTO);
}
