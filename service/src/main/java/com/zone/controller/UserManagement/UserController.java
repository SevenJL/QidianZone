package com.zone.controller.UserManagement;

import com.zone.constant.MessageConstant;
import com.zone.entity.User;
import com.zone.result.Result;
import com.zone.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * 有关用户的管理
 */

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
    public Result<String> register(@RequestBody User user){

        // 注册
        log.info("注册用户:{}",user);

        // 将用户注册的数据传给数据库
        Integer userId = userService.register(user.getPassword(),user.getName(),user.getEmail());

        // 判断是否注册成功
        if(userId == null && userId == -1){
            return Result.error(MessageConstant.ACCOUNT_EXISTING); // 账户已存在
        }

        // 数据传入数据库后返回成功
        log.info("用户:{}注册成功",user.getName());

        return Result.success("注册成功");

    }








}
