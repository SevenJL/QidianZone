package com.zone.service;


import com.zone.dto.PageSearchDTO;
import com.zone.result.PageResult;

/**
 * 回收站服务
 */
public interface RecycleBinService {


    /**
     * 清理
     */
    void clear();


    /**
     * 显示
     * @return
     */
    PageResult show(PageSearchDTO pageSearchDTO);
}
