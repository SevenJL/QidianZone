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
    public PageResult list(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        Page<User> pageUser = userMapper.pageQuery(page, pageSize);
        long total = pageUser.getTotal();
        List<User> result = pageUser.getResult();

        return new PageResult(total, result);
    }
}
