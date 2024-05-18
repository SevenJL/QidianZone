package com.zone.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zone.dto.PageSearchDTO;
import com.zone.entity.Article;
import com.zone.result.PageResult;

/**
 * 回收站服务
 */
public interface RecycleBinService extends IService<Article> {

    /**
     * 清理
     */
    void clear();

    /**
     * 显示
     */
    PageResult show(PageSearchDTO pageSearchDTO);
}
