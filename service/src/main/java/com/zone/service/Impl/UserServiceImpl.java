package com.zone.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zone.constant.PowerConstant;
import com.zone.constant.RegisterConstant;
import com.zone.context.BaseContext;
import com.zone.dto.LoginDTO;
import com.zone.dto.PageBean;
import com.zone.dto.UserUpdatePasswordDTO;
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
     * @param userUpdatePasswordDTO 修改密码信息
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
        Integer id = BaseContext.getCurrentId();
        user.setId(id); // 设置id
        userMapper.updateById(user); // 修改密码

        return id;
    }

    /**
     * 修改昵称
     */
    @Override
    public void updateNickName(Integer id, String nickName) {
        userMapper.updateNickname(nickName, id);
    }


    /**
     * 修改头像
     */
    @Override
    public void updateAvatar(Integer id, String avatarUrl) {
        // 创建user对象
        User user = new User();
        user.setAvatarUrl(avatarUrl);
        user.setId(id);
        log.info("avatarUrl:{}", avatarUrl);

        // 修改
        userMapper.updateById(user);

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
