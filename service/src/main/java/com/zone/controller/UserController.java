package com.zone.controller;

import com.zone.dto.RegisterDTO;
import com.zone.dto.UserUpdatePasswordDTO;
import com.zone.entity.User;
import com.zone.result.Result;
import com.zone.service.UserService;
import com.zone.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@Slf4j
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    @ApiOperation("注册")
    @Transactional
    public Result<Integer> register(@RequestBody RegisterDTO registerDTO){

        // 注册
        log.info("注册用户:{}",registerDTO);

        //TODO 使用MD5加密密码 再传入数据库

        // 将用户注册的数据传给数据库
        Integer update =  userService.register(registerDTO.getPassword(),registerDTO.getName(),registerDTO.getEmail());

        // 判断是否注册成功
        if(update == -1){
            return Result.error("注册失败");
        }

        // 数据传入数据库后返回成功
        log.info("用户:{}注册成功",registerDTO.getName());
        return Result.success();

    }

    /**
     * 修改密码
     * @param userUpdatePasswordDTO 修改密码的DTO对象
     */
    @GetMapping("/updatePassword")
    @ApiOperation("修改密码")
    public Result<Object> updatePassword(@RequestBody UserUpdatePasswordDTO userUpdatePasswordDTO){
        log.info("修改密码:{}",userUpdatePasswordDTO);
        // TODO 需要对修改的密码进行MD5加密
        // 传入DTO对象
        Integer userId =  userService.update(userUpdatePasswordDTO);

        // 如果用户不存在
        if(userId == -1){
            return Result.error("用户不存在");
        }

        log.info("修改密码成功");
        return Result.success("修改密码成功");
    }

    /**
     * 修改昵称
     *
     */

    @GetMapping("/updateNickname")
    @ApiOperation("修改昵称")
    public Result<Object> updateNickname(@RequestParam("userId") Integer userId,@RequestParam("nickname") String nickname){
        log.info("修改昵称:{},id:{}",nickname,userId);

        // 传入DTO对象
        userService.updateNickname(userId,nickname);

        log.info("修改昵称成功");
        return Result.success("修改昵称成功");
    }

    /**
     * 修改头像
     */

    @GetMapping("/updateAvatar")
    @ApiOperation("修改头像")
    public Result<Object> updateAvatar(@RequestParam("userId") Integer userId,
                                       @RequestParam("avatarUrl") String avatarUrl){
        log.info("修改头像:{},id:{}",avatarUrl,userId);

        // 传入DTO对象
        userService.updateAvatar(userId,avatarUrl);

        log.info("修改头像成功");
        return Result.success("修改头像成功");
    }


    /**
     * 获取用户最基本信息
     *
     */

    @GetMapping("/getUserInfo")
    @ApiOperation("获取用户最基本信息")
    public Result<UserVO> getUserInfo(@RequestParam("userId") Integer userId){
        log.info("获取用户信息:{}",userId);

        // 传入DTO对象
        User user = userService.getUserInfo(userId);

        // 将获取的User 对象 拷贝到 UserVO
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        // 返回用户基本信息
        log.info("获取用户信息成功");
        return Result.success(userVO);
    }



}
