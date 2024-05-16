package com.zone.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zone.dto.ArticleEditDTO;
import com.zone.dto.ArticlePublishDTO;
import com.zone.dto.PageSearchDTO;
import com.zone.entity.Article;
import com.zone.result.PageResult;

import java.util.List;

/**
 * 用户服务
 */
public interface ArticleService extends IService<Article> {


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

    /**
     * 根据文章题目进行模糊搜索
     */
    PageResult search(PageSearchDTO pageSearchDTO);

    /**
     * 批量删除文章
     */
    void deleteArticleByIds(List<Integer> ids);
}
