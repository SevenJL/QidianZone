package com.zone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.zone.dto.PageSearchDTO;
import com.zone.entity.Article;
import com.zone.handler.JsonTypeHandler;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ArticleMapper extends BaseMapper<Article>  {

    /**
     * 发布文章
     */
    @Insert("insert into article(title,content,creator,status,delete_status,create_time,update_time ,article_view_power," +
            "article_comment_count,article_like,article_view) " +
            "values(#{title},#{content},#{creator},#{status},#{deleteStatus},#{createTime},#{updateTime},#{articleViewPower}" +
            ",#{articleCommentCount},#{articleLike},#{articleView})"  )
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertArticle(Article article);


    /**
     * 更新文章数据
     */
    void update(Article article);


    /**
     * 根据  大致标题进行模糊查询   文章分类/标签分类进行精确查询
     */
    @Results({
            @Result(column = "tags", property = "tags", typeHandler = JsonTypeHandler.class)
    })
    Page<Article> search(PageSearchDTO pageSearchDTO);


    /**
     * 根据文章标题进行精确查询
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Select("SELECT * FROM article WHERE title = #{title}")
    Integer findByTitle(@Param("title") String title);
}
