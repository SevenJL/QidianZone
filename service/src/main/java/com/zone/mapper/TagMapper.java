package com.zone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zone.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 文章标签
 */

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据标签名称查询标签id
     */
    @Select("SELECT id FROM tag WHERE name = #{name}")
    Integer findByTagName(String name);

}
