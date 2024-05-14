package com.zone.mapper;


import com.zone.vo.ArticleRecycleBinVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface RecycleBinMapper {


    /**
     * 清理回收站
     */
    void clear();


    /**
     * 显示文章信息
     */
    List<ArticleRecycleBinVO> show();

}
