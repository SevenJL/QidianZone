package com.zone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zone.entity.ArticleTag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 文章标签
 */

@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {


    /**
     * 根据文章id删除标签
     */
    @Select("SELECT tag_id FROM article_tag WHERE article_id = #{id}")
    void deleteByArticleId(@Param("id") Integer id);

    /**
     * 插入标签
     */
    @Insert("INSERT INTO article_tag(article_id,tag_id) VALUES (#{articleId},#{tagId})")
    void insertArticleTag(ArticleTag articleTag);
}
