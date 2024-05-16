package com.zone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.zone.dto.PageSearchDTO;
import com.zone.entity.Article;
import com.zone.entity.NewArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article>  {



    /**
     * 更新 文章数据
     */
    void update(Article article);


    /**
     * 根据 大致标题进行模糊查询  文章分类/标签分类进行精确查询
     */
    Page<Article> search(PageSearchDTO pageSearchDTO);


    /**
     * 获取 最新文章
     */
    @Select("select * from article order by create_time desc limit 5")
    List<NewArticle> listNew();

}
