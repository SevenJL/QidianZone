package com.zone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.zone.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecycleBinMapper extends BaseMapper<Article> {


    /**
     * 清理回收站
     */
    void clear();


    /**
     * 显示文章信息
     */
    Page<Article> show();

}
