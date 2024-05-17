package com.zone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zone.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecycleBinMapper extends BaseMapper<Article> {


    /**
     * 清理回收站
     */
    void clear();

    /**
     * 查询回收站的文章
     */
    List<Article> findDeleteStatusEqualZero();


    /**
     * 显示文章信息
     */
//    Page<Article> show();

}
