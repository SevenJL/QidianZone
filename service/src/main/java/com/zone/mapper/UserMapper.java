package com.zone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.zone.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 注册用户
     * 插入数据
     */

    int insert(@Param("user") User user);


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

    /**
     * 查询用户信息
     */
    @Select("SELECT * FROM user")
    Page<User> listUser();
}
