package com.zone.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper  {

    /**
     * 注册用户
     * 插入数据
     * @param password 密码
     * @param userName 用户名
     * @param email 邮箱
     * @return 用户注册的id
     */

    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(@Param("password") String password,@Param("userName") String userName,@Param("email") String email);


    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return 布尔值
     * true: 用户名已存在
     */
    @Select("SELECT * FROM user WHERE user_name = #{userName}")
    Integer findByUserName(@Param("userName") String userName);

    /**
     * 登录
     * @param password 密码
     * @param userName 用户名
     */
    @Select("SELECT user_id FROM user WHERE user_name = #{userName} AND password = #{password}")
    Integer login(@Param("password")String password, @Param("userName") String userName);
}
