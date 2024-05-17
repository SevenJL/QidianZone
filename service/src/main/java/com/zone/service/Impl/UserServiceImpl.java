package com.zone.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zone.constant.PowerConstant;
import com.zone.constant.RegisterConstant;
import com.zone.context.BaseContext;
import com.zone.dto.LoginDTO;
import com.zone.dto.PageBean;
import com.zone.dto.UserUpdateDTO;
import com.zone.entity.User;
import com.zone.mapper.UserMapper;
import com.zone.result.PageResult;
import com.zone.service.UserService;
import com.zone.vo.UserManageVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

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
                .nickName(name)
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
        Integer id = userMapper.login(loginDTO.getPassword(), loginDTO.getName());

        // 如果id为空 则登录失败
        if (id == null) {
            // 登录失败
            // 返回-1 表示登录失败
            log.info("用户:{}登录失败", loginDTO.getName());
            return -1;
        }

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
        userUpdateDTO.setPassword(password);
        userUpdateDTO.setId(BaseContext.getCurrentId());
        userMapper.updateUser(userUpdateDTO);
    }

    /**
     * 修改昵称
     */
    @Override
    public void updateNickName(String nickName) {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setNickName(nickName);
        userUpdateDTO.setId(BaseContext.getCurrentId());
        userMapper.updateUser(userUpdateDTO);
    }


    /**
     * 修改头像
     */
    @Override
    public void updateAvatar(String avatarUrl) {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setAvatarUrl(avatarUrl);
        userUpdateDTO.setId(BaseContext.getCurrentId());
        userMapper.updateUser(userUpdateDTO);
    }

    /**
     * 获取用户最基本信息
     */
    @Override
    public User getUserInfo(Integer id) {
        return userMapper.getUserInfo(id);
    }

    /**
     * 列出用户信息
     */
    @Override
    public PageResult listUser(PageBean pageBean) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        // 1.获取用户数据集合
        Page<User> userList = userMapper.listUser();

        // 2.拷贝数据
        List<UserManageVO> userManageVOS = new ArrayList<>();
        userList.forEach(user -> {
            // 2.1创建VO对象
            UserManageVO userManageVO = new UserManageVO();
            BeanUtils.copyProperties(user, userManageVO);
            // 2.2存放在新的集合中
            userManageVOS.add(userManageVO);
        });

        // 3.返回集合
        return new PageResult(userList.getTotal(),userManageVOS);

    }
}
