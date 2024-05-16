package com.zone.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper  {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Select("SELECT * FROM admin WHERE name = #{name} AND password = #{password}")
    Integer Login(@Param("password") String password, @Param("name") String name);

}
