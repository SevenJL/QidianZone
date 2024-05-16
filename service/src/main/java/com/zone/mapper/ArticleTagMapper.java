package com.zone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zone.entity.ArticleTag;
import org.apache.ibatis.annotations.*;

/**
 * 文章标签
 */

@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {


    @Select("SELECT tag_id FROM article_tag WHERE article_id = #{id}")
    void deleteByArticleId(@Param("id") Integer id);

    @Insert("INSERT INTO article_tag(article_id,tag_id) VALUES (#{articleId},#{tagId})")
    void insertArticleTag(ArticleTag articleTag);
}
