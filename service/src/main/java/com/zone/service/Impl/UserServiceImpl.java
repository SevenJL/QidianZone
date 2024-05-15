package com.zone.service.Impl;

import com.zone.constant.PowerConstant;
import com.zone.constant.RegisterConstant;
import com.zone.dto.LoginDTO;
import com.zone.dto.UserUpdatePasswordDTO;
import com.zone.entity.User;
import com.zone.service.UserService;
import com.zone.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;



@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;

    @Override
    public Integer register(String password, String name, String email) {
        // 先判断 数据库中是否含有同名的用户
        if (userMapper.findByUserName(name) != null ) {
            // 存在同名用户 注册失败
            // 返回-1 表示注册失败
            return -1;
        }
        // 说明没有同名的用户
        // 更新数据
        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .nickname(name)
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
        Integer userId = userMapper.login(loginDTO.getPassword(), loginDTO.getName());

        // 如果userid为空 则登录失败
        if (userId == null) {
            // 登录失败
            // 返回-1 表示登录失败
            log.info("用户:{}登录失败", loginDTO.getName());
            return -1;
        }

        return userId;
    }


    /**
     * 修改密码
     * @param userUpdatePasswordDTO 修改密码信息
     * @return
     */
    @Override
    @Transactional
    public Integer update(UserUpdatePasswordDTO userUpdatePasswordDTO) {
        // 修改密码
        // 先查询用户是否存在
        if (userMapper.findByUserName(userUpdatePasswordDTO.getName()) == null) {
            // 用户不存在
            // 返回-1 表示修改失败
            log.info("用户:{}不存在", userUpdatePasswordDTO.getName());
            return -1;
        }

        // 拷贝数据
        User user = new User();
        BeanUtils.copyProperties(userUpdatePasswordDTO, user);
        log.info("用户:{}修改密码", userUpdatePasswordDTO.getName());

        // 根据用户名 查询id
        Integer byUserName = userMapper.findByUserName(userUpdatePasswordDTO.getName());
        user.setId(byUserName); // 设置id
        userMapper.update(user); // 修改密码

        return byUserName;
    }

    /**
     * 修改昵称
     * @param userId   用户id
     * @param nickname 昵称
     */
    @Override
    public void updateNickname(Integer userId, String nickname) {
        userMapper.updateNickname(nickname, userId);
    }


    /**
     * 修改头像
     * @param userId 用户id
     */
    @Override
    public void updateAvatar(Integer userId, String avatarUrl) {
        User user = new User();
        user.setAvatarUrl(avatarUrl);
        user.setId(userId);
        log.info("avatarUrl:{}", avatarUrl);
        userMapper.update(user);

    }

    /**
     * 获取用户最基本信息
     */
    @Override
    public User getUserInfo(Integer userId) {
        return userMapper.getUserInfo(userId);
    }
}
