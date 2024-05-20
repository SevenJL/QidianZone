package com.zone.service.Impl.user;

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

/**
 * 用户服务实现
 */

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;


    /**
     * 注册
     */
    @Override
    public Integer register(String password, String account, String email) {
        // 1.先判断 数据库中是否含有同名的用户
        if (userMapper.findByUserAccount(account) != null ) {
            // 存在同名用户 注册失败
            // 返回-1 表示注册失败
            return -1;
        }

        // 2.说明没有同名的用户
        // 创建User对象 进行注册
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
        userMapper.insert(user);

        // 3.插入数据
        return user.getId();
    }


    /**
     * 登录
     */
    @Override
    public Integer login(LoginDTO loginDTO) {

        // 1.登录
        Integer id = userMapper.login(loginDTO.getPassword(), loginDTO.getAccount());

        // 2.如果id为空 则登录失败
        if (id == null) {
            // 登录失败
            // 返回-1 表示登录失败
            log.info("用户:{}登录失败", loginDTO.getAccount());
            return -1;
        }

        // 3.登录成功
        return id;
    }


    /**
     * 修改密码
     */
    @Override
    @Transactional
    public void updatePassword(String password) {
        // 拷贝数据
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setPassword(password); // 设置密码
        userUpdateDTO.setId(BaseContext.getCurrentId()); // 设置id
        userMapper.updateUser(userUpdateDTO); // 更新数据
    }


    /**
     * 修改昵称
     */
    @Override
    public void updateNickName(String nickName) {
        // 拷贝数据
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setNickName(nickName); // 设置昵称
        userUpdateDTO.setId(BaseContext.getCurrentId()); // 设置id
        userMapper.updateUser(userUpdateDTO); // 更新数据
    }

    /**
     * 修改头像
     */
    @Override
    public void updateAvatar(String avatarUrl) {
        // 拷贝数据
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setAvatarUrl(avatarUrl); // 设置头像
        userUpdateDTO.setId(BaseContext.getCurrentId()); // 设置id
        userMapper.updateUser(userUpdateDTO); // 更新数据
    }

    /**
     * 获取用户信息
     */
    @Override
    public User getUserInfo(Integer id) {
        return userMapper.getUserInfo(id);
    }
}
