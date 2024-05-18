package com.zone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zone.dto.AdminDTO;
import com.zone.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper extends BaseMapper<Admin>{

    /**
     * 登录功能
     */
    @Select("SELECT * FROM admin WHERE account = #{account} AND password = #{password}")
    Integer Login(@Param("password") String password, @Param("account") String account);

    /**
     * 删除用户
     */
    @Delete("delete from user where id = #{id}")
    void deleteById(@Param("id") String id);

    /**
     * 修改管理员账号
     */
    void updateAdmin(AdminDTO adminDTO);
}
