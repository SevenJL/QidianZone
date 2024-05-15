package com.zone.mapper;


import com.zone.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper  {

    /**
     * 注册用户
     * 插入数据
     */

    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(@Param("user") User user);


    /**
     * 根据用户名查询用户
     */
    @Select("SELECT * FROM user WHERE name = #{name}")
    Integer findByUserName(@Param("name") String name);

    /**
     * 登录
     */
    @Select("SELECT id FROM user WHERE name = #{name} AND password = #{password}")
    Integer login(@Param("password")String password, @Param("name") String name);

    /**
     * 更新用户信息
     */
    void update(@Param("user") User user);

    /**
     * 修改昵称
     */
    @Update("UPDATE user SET nick_name = #{nickname} WHERE id = #{userId}")
    void updateNickname(@Param("nickname") String nickname, @Param("userId") Integer userId);

    /**
     * 修改头像
     */
    @Select("SELECT * FROM user WHERE id = #{userId}")
    User getUserInfo(@Param("userId") Integer userId);
}
