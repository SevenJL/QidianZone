package com.zone.service.Impl;

import com.zone.dto.LoginDTO;
import com.zone.mapper.AdminMapper;
import com.zone.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {
    private final AdminMapper adminMapper;

    @Override
    public Integer login(LoginDTO loginDTO) {
        // 获取账号密码
        String password = loginDTO.getPassword();
        String name = loginDTO.getName();

        // 进行查询管理员 是否存在
        Integer admin = adminMapper.Login(password,name);

        if (admin == -1 ) {
            // 未查询到该管理员
            log.info("未查询到该管理员");
            return -1;
        }
        // 查询到该管理员
        return admin;

    }
}
