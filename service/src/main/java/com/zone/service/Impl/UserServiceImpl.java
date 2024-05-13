package com.zone.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zone.constant.PowerConstant;
import com.zone.constant.StatusConstant;
import com.zone.entity.User;
import com.zone.service.UserService;
import com.zone.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer register(String password, String name, String email) {
        // 先判断 数据库中是否含有同名的用户
        if (userMapper.findByUserName(name) != null ) {
            // 存在同名用户 注册失败
            // 返回-1 表示注册失败
            return -1;
        }
        if (userMapper.findUserByEmail(email) != null) {
            // 存在同名邮箱 注册失败
            // 返回-1 表示注册失败
            return -1;
        }

        //TODO 根据用户输入的密码 进行MD5加密 再存储到数据困中 避免密码泄露

        // 说明没有同名的用户
        // 更新信息
        LocalDateTime updateTime = LocalDateTime.now(); // 更新时间
        LocalDateTime createTime = LocalDateTime.now(); // 创建时间
        Integer deleteStatus = StatusConstant.ENABLE; // 删除状态
        Integer status = StatusConstant.ENABLE; // 在线状态

        //注册用户 默认为user 管理员只能由当前的管理者进行更改power
        String power = PowerConstant.DEFAULT_POWER; // user权限

        // 返回生成的ID
        return userMapper.insert(password,name,email,updateTime,createTime,status,power);
    }


    @Override
    public Integer login(String password, String userName) {
        // 登录
        Integer userId = userMapper.login(password,userName);

        // TODO 使用jwt令牌进行验证用户身份

        // 如果userid为空 则登录失败
        if (userId == null) {
            // 登录失败
            // 返回-1 表示登录失败
            return -1;
        }

        return userId;

    }



    @Override
    public void logout(Integer currentId) {
        //TODO 使用ThreadLocal获取当前用户的ID

        // 更新用户状态
        Integer status = StatusConstant.DISABLE;
        userMapper.logout(currentId,status);
        log.info("用户退出成功");
    }

    /**
     * 根据id删除用户
     * @param id 用户id
     * @return
     */
    @Override
    public Boolean deleteById(Integer id,Integer status) {
        // 先查询 用户是否存在 和 状态是否为 离线
        User user = userMapper.query(id,status);

        if (user == null){
            // 说明用户不存在
            log.info("用户不存在");
            return false;
        }
        if (status == StatusConstant.ENABLE){
            // 说明用户在线
            log.info("用户在线");
            return false;
        }

        // 说明用户 存在 且 为离线
        // 此时可以删除用户
        // 但实际上为逻辑删除 并不是真正删除
        user.setDeleteStatus(StatusConstant.ENABLE); // 更改用户的删除状态
        userMapper.updateById(user);

        return true;
    }
}
