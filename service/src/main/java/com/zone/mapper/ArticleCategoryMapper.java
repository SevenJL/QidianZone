package com.zone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zone.entity.ArticleCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {

    /**
     * 插入文章和分类关联
     */
    @Insert("INSERT INTO article_category(article_id,category_id) VALUES (#{articleId},#{categoryId})")
    int insert(ArticleCategory articleCategory);

    /**
     * 根据文章id查询分类
     */
    List<String> findByArticleId(@Param("id") Integer id);

    /**
     * 根据文章id删除分类
     */
    void deleteByArticleId(@Param("id") Integer id);

    /**
     * 根据文章id批量删除分类
     */
    void deleteByArticleIds(List<Integer> ids);
}
