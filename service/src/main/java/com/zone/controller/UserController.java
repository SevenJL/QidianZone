package com.zone.controller;

import com.zone.dto.UserUpdatePasswordDTO;
import com.zone.entity.User;
import com.zone.result.Result;
import com.zone.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<Integer> register(@RequestBody User user){

        // 注册
        log.info("注册用户:{}",user);

        // 将用户注册的数据传给数据库
        Integer userId = userService.register(user.getPassword(),user.getName(),user.getEmail());

        // 判断是否注册成功
        if(userId == null && userId == -1){
            return Result.error("注册失败");
        }

        // 数据传入数据库后返回成功
        log.info("用户:{}注册成功",user.getName());
        return Result.success(userId);

    }

    /**
     * 修改密码
     * @param userUpdatePasswordDTO 修改密码的DTO对象
     */
    @GetMapping("/updatePassword")
    @ApiOperation("修改密码")
    public Result<Object> updatePassword(@RequestBody UserUpdatePasswordDTO userUpdatePasswordDTO){
        log.info("修改密码:{}",userUpdatePasswordDTO);
        // 传入DTO对象
        Integer userId =  userService.update(userUpdatePasswordDTO);

        // 如果用户不存在
        if(userId == -1){
            return Result.error("用户不存在");
        }

        log.info("修改密码成功");
        return Result.success("修改密码成功");
    }




}
