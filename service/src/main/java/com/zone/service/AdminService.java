package com.zone.service;

import com.zone.result.PageResult;

/**
 * 管理服务
 */
public interface AdminService {


    /**
     * 查询所有用户
     */

    PageResult list(Integer page, Integer pageSize);
}
