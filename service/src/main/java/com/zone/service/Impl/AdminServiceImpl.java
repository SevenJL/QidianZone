package com.zone.service.Impl;


import com.zone.entity.User;
import com.zone.mapper.AdminMapper;
import com.zone.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;


    @Override
    public List<User> listAllUsers() {
        log.info("查询所有的用户信息");

        return adminMapper.listAllUsers();
    }
}
