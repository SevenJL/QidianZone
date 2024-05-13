package com.zone.service.Impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zone.entity.User;
import com.zone.mapper.AdminMapper;
import com.zone.mapper.UserMapper;
import com.zone.result.PageResult;
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
    @Autowired
    private UserMapper userMapper;




    @Override
    public PageResult<User> list(Integer page, Integer pageSize) {
        // 分页查询
        // 先设置分页参数
        PageHelper.startPage(page, pageSize);

        // 执行查询
        List<User> userList = userMapper.list();
        Page<User> userPage = (Page<User>) userList;

        // 封装成分页结果返回
        return new PageResult<>(userPage.getTotal(), userPage.getResult());
    }
}
