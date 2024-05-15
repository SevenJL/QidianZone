package com.zone.mapper;


import com.github.pagehelper.Page;
import com.zone.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecycleBinMapper {


    /**
     * 清理回收站
     */
    void clear();


    /**
     * 显示文章信息
     */
    Page<Article> show();

}
