package com.zone.mapper;


import com.github.pagehelper.Page;
import com.zone.dto.PageSearchDTO;
import com.zone.entity.Article;
import com.zone.handler.JsonTypeHandler;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

@Mapper
public interface ArticleMapper {

    /**
     * 发布文章
     */
    Integer insert(Article article);


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
    Integer findByTitle(String title);
}
