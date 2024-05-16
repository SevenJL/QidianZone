package com.zone.mapper;


import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminMapper  {

    /**
     * 登录功能
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Select("SELECT * FROM admin WHERE name = #{name} AND password = #{password}")
    Integer Login(@Param("password") String password, @Param("name") String name);


    /**
     * 删除用户
     */
    @Delete("delete from user where id = #{id}")
    void deleteById(@Param("id") String id);
}
