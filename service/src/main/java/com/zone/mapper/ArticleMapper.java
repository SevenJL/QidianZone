package com.zone.mapper;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zone.dto.PageSearchDTO;
import com.zone.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {

    /**
     * 发布文章
     */
    void insert(Article article);

    /**
     * 更新文章数据
     */
    void update(Article article);


    /**
     * 根据  大致标题进行模糊查询   文章分类/标签分类进行精确查询
     */
    List<Article> search(PageSearchDTO pageSearchDTO);
}
