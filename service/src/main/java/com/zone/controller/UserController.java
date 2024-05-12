package com.zone.controller;

import com.zone.entity.User;
import com.zone.restful.Result;
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

    @GetMapping("/login")
    @ApiOperation("登录")
    public Result<Object> login(@RequestBody User user){
        // 登录
        log.info("登录用户:{}",user);

        // 获得密码和账户名
        String password = user.getPassword();
        String name = user.getName();

        // 传入数据库 进行对比
        Integer userId = userService.login(password,name);
        if (userId == null && userId == -1){
            return Result.error("登录失败");
        }

        // 登录成功
        log.info("用户:{}登录成功",name);
        return Result.success();
    }

    @ApiOperation("退出")
    @GetMapping("/logout")
    public Result logout(){
        // TODO 使用redis进行用户的状态登录和退出的记录
        // stats = 1 -> status = 0;
        log.info("退出");

        return Result.success();
    }

}
