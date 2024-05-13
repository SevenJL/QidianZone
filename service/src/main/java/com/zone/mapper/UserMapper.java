package com.zone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zone.entity.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 注册用户
     * 插入数据
     * @param password 密码
     * @param userName 用户名
     * @param email 邮箱
     * @return 用户注册的id
     */

    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(@Param("password") String password,
                   @Param("userName") String userName,
                   @Param("email") String email,
                   @Param("power") LocalDateTime power,
                   @Param("createTime") LocalDateTime createTime,
                   @Param("updateTime") Integer updateTime,
                   @Param("status")String status);


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
    Integer login(@Param("password")String password,@Param("userName") String userName);


    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return 当前用户的ID
     */
    @Select("SELECT user_id FROM user WHERE email = #{email}")
    Integer findUserByEmail(@Param("email") String email);


    /**
     * 退出
     * @param id 当前用户的ID
     */
    @Update("UPDATE user SET status = #{status} WHERE user_id = #{id}")
    void logout(@Param("id") Integer id, @Param("status") Integer status);

    /**
     * 查询当前用户信息
     * @param id 当前用户的ID
     * @return 当前用户的信息
     */
    @Select("SELECT * FROM user WHERE user_id = #{id}")
    User query(@Param("id") Integer id);
}
