package com.zone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zone.entity.ArticleTag;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 文章标签
 */

@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {


    /**
     * 根据文章id删除标签
     */
    @Delete("DELETE FROM article_tag WHERE article_id = #{id}")
    void deleteByArticleId(@Param("id") Integer id);

    /**
     * 插入标签
     */
    @Insert("INSERT INTO article_tag(article_id,tag_id) VALUES (#{articleId},#{tagId})")
    void insertArticleTag(ArticleTag articleTag);


    /**
     * 根据文章id查询标签
     */
    @Select("SELECT tag_id FROM article_tag WHERE article_id = #{id}")
    List<String> findByArticleId(@Param("id") Integer id);

    /**
     * 根据文章id集合批量删除
     */
    void deleteByArticleIds(List<Integer> ids);
}
