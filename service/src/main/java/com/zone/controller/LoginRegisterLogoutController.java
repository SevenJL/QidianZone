package com.zone.controller;


import com.zone.dto.RegisterDTO;
import com.zone.result.Result;
import com.zone.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录注册注销
 */


@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginRegisterLogoutController {

    private final UserService userService;


    @GetMapping("/register")
    @Transactional // 事务
    public Result<String> register(@RequestBody RegisterDTO registerDTO){

        // 1.注册
        log.info("注册用户:{}",registerDTO);

        //TODO 使用MD5加密密码 再传入数据库

        // 2.将用户注册的数据传给数据库
        Integer update =  userService.register(registerDTO.getPassword(),registerDTO.getAccount(),registerDTO.getEmail());

        // 3.判断是否注册成功
        if(update == -1){
            return Result.error("注册失败");
        }

        // 4.数据传入数据库后返回成功
        log.info("用户:{}注册成功",registerDTO.getAccount());
        return Result.success("注册成功");

    }

    @GetMapping("/logout")
    public Result<Object> logout(){
        log.info("退出");
        // TODO 使用redis进行用户的状态登录和退出的记录
        log.info("退出");

        return Result.success();
    }
}
