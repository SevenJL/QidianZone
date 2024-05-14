package com.zone.service;


import com.zone.dto.ArticleEditDTO;
import com.zone.dto.ArticlePublishDTO;

/**
 * 用户服务
 */
public interface ArticleService {


    /**
     * 发布文章
     */
    void publish(ArticlePublishDTO articlePublishDTO);

    /**
     * 编辑文章
     */
    void edit(ArticleEditDTO articleEditDTO);


    /**
     * 删除文章
     * 逻辑删除
     */
    void delete(Integer id);
}
