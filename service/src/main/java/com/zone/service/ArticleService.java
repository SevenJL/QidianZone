package com.zone.service;


import com.zone.dto.ArticlePublishDTO;

/**
 * 用户服务
 */
public interface ArticleService {


    /**
     * 发布文章
     */
    void publish(ArticlePublishDTO articlePublishDTO);
}
