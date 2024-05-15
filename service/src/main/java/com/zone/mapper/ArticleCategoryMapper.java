package com.zone.mapper;


import com.zone.entity.ArticleCategory;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleCategoryMapper {


    /**
     * 插入文章和分类关联
     */
    @Insert("INSERT INTO article_category(article_id,category_id) VALUES (#{articleId},#{categoryId})")
    void insert(ArticleCategory articleCategory);

    /**
     * 根据文章id查询分类
     */
    List<String> findByArticleId(@Param("id") Integer id);
}
