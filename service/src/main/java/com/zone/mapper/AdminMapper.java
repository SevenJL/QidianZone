package com.zone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zone.dto.AdminDTO;
import com.zone.dto.LoginDTO;
import com.zone.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 管理员
 */

@Mapper
public interface AdminMapper extends BaseMapper<Admin>{

    /**
     * 登录功能
     */
    @Select("SELECT * FROM admin WHERE account = #{account} AND password = #{password}")
    Integer Login(LoginDTO loginDTO);

    /**
     * 修改管理员账号
     */
    void updateAdmin(AdminDTO adminDTO);
}
