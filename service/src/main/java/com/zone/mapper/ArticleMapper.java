package com.zone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.zone.dto.PageSearchDTO;
import com.zone.entity.Article;
import com.zone.entity.NewArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 文章
 */

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 更新 文章数据
     */
    void updateArticle(Article article);

    /**
     * 根据大致标题 进行模糊查询 <br>
     * 文章分类/标签分类 进行精确查询
     */
    Page<Article> search(PageSearchDTO pageSearchDTO);

    /**
     * 获取 最新文章 5篇
     */
    @Select("select * from article order by create_time desc limit 5")
    List<NewArticle> listNew();

    /**
     * 获取 所有人的文章
     */
    @Select("select * from article order by create_time desc")
    Page<Article> listAllArticle();

    /**
     * 获取 文章
     */
    @Select("select * from article where id = #{articleId}")
    Article checkOutArticle(@Param("articleId") Integer articleId);

    /**
     * 增加 文章浏览量
     */
    @Update("update article set article_view = article_view + 1 where id = #{articleId}")
    void addArticleView(@Param("articleId") Integer articleId);

    /**
     * 增加 文章点赞量
     */
    @Update("update article set article_like = article_like + 1 where id = #{articleId}")
    void updateArticleLike(@Param("articleId") Integer articleId);

    /**
     * 增加 文章评论量
     */
    @Update("update article set article_reply_count = article_reply_count + 1 where id = #{articleId}")
    void updateReplyCount(@Param("articleId") Integer articleId);

    /**
     * 获取 个人文章
     */
    @Select("select * from article where creator_id = #{id} order by create_time desc")
    Page<Article> listPersonalArticle(@Param("id") Integer id);
}
