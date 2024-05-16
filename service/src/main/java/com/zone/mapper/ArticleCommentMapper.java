package com.zone.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ArticleCommentMapper {

    @Select("select * from comment where article_id = #{id}")
    List<String> getCommentByArticleId();

}
