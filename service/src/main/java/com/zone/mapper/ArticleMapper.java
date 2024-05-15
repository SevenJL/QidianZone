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
     * 更新文章数据
     */
    void update(Article article);


    /**
     * 根据  大致标题进行模糊查询   文章分类/标签分类进行精确查询
     */

    Page<Article> search(PageSearchDTO pageSearchDTO);


}
