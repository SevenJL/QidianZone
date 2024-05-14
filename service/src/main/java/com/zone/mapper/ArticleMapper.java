package com.zone.mapper;


import com.zone.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper {
    /**
     * 发布文章
     */
    void insert(@Param("article") Article article);

}
