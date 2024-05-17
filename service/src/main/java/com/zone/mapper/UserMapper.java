package com.zone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.zone.dto.UserUpdateDTO;
import com.zone.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 注册用户
     * 插入数据
     */

    int insertUser(User user);


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
     * 查询用户信息
     */
    @Select("SELECT * FROM user")
    Page<User> listUser();

    /**
     * 修改用户信息
     */
    void updateUser(UserUpdateDTO userUpdateDTO);

    /**
     * 根据id查询用户信息
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User getUserInfo(@Param("id") Integer id);
}
