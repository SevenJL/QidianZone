package com.zone.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zone.constant.PowerConstant;
import com.zone.constant.RegisterConstant;
import com.zone.context.BaseContext;
import com.zone.dto.LoginDTO;
import com.zone.dto.UserUpdateDTO;
import com.zone.entity.User;
import com.zone.mapper.UserMapper;
import com.zone.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    @Override
    public Integer register(String password, String account, String email) {
        // 先判断 数据库中是否含有同名的用户
        if (userMapper.findByUserAccount(account) != null ) {
            // 存在同名用户 注册失败
            // 返回-1 表示注册失败
            return -1;
        }
        // 说明没有同名的用户
        // 更新数据
        User user = User.builder()
                .account(account)
                .email(email)
                .password(password)
                .nickName(account)
                .power(PowerConstant.DEFAULT_POWER)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .deleteStatus(RegisterConstant.DEFAULT_DELETE_STATUS)
                .status(RegisterConstant.DEFAULT_STATUS)
                .deleteTime(RegisterConstant.DEFAULT_DELETE_TIME)
                .articleCount(RegisterConstant.DEFAULT_ARTICLE_COUNT)
                .avatarUrl(RegisterConstant.DEFAULT_AVATAR_URL)
                .articleLike(RegisterConstant.DEFAULT_ARTICLE_LIKE_VALUE)
                .build();
        log.info("用户:{}", user);
        return userMapper.insert(user);
    }


    @Override
    public Integer login(LoginDTO loginDTO) {

        // 登录
        Integer id = userMapper.login(loginDTO.getPassword(), loginDTO.getAccount());

        // 如果id为空 则登录失败
        if (id == null) {
            // 登录失败
            // 返回-1 表示登录失败
            log.info("用户:{}登录失败", loginDTO.getAccount());
            return -1;
        }

        return id;
    }


    @Override
    @Transactional
    public void updatePassword(String password) {
        // 拷贝数据
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setPassword(password);
        userUpdateDTO.setId(BaseContext.getCurrentId());
        userMapper.updateUser(userUpdateDTO);
    }


    @Override
    public void updateNickName(String nickName) {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setNickName(nickName);
        userUpdateDTO.setId(BaseContext.getCurrentId());
        userMapper.updateUser(userUpdateDTO);
    }



    @Override
    public void updateAvatar(String avatarUrl) {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setAvatarUrl(avatarUrl);
        userUpdateDTO.setId(BaseContext.getCurrentId());
        userMapper.updateUser(userUpdateDTO);
    }


    @Override
    public User getUserInfo(Integer id) {
        return userMapper.getUserInfo(id);
    }



}
