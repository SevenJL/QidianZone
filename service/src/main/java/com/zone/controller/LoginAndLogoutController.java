package com.zone.controller;

import com.zone.dto.LoginDTO;
import com.zone.result.Result;
import lombok.extern.slf4j.Slf4j;
import com.zone.service.UserService;
import com.zone.service.AdminService;
import lombok.RequiredArgsConstructor;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping
@RestController
@RequiredArgsConstructor // 构造函数注入Bean
public class LoginAndLogoutController {

    private final UserService userService;

    private final AdminService adminService;

    @GetMapping("/login")
    @ApiOperation("登录")
    public Result<Object> login(@RequestBody LoginDTO loginDTO){
        // 1.登录
        log.info("登录:{}",loginDTO);
        //TODO 输入的密码进行MD5加密

        // 2.如果是用户登录
        if (loginDTO.getPower().equals("user")){
            // 获得用户id
            Integer userId = userService.login(loginDTO);
            if (userId == null || userId == -1){
                return Result.error("登录失败");
            }
            // 登录成功
            log.info("用户:{}登录成功",loginDTO.getName());
            return Result.success();
        }

        // 3.如果是管理员登录
        if (loginDTO.getPower().equals("admin")){
            // 获得用户id
            Integer userId = adminService.login(loginDTO);
            if (userId == null || userId == -1){
                return Result.error("登录失败");
            }
            // 登录成功
            log.info("管理员:{}登录成功",loginDTO.getName());
            return Result.success();
        }


        return Result.success("登录成功");
    }

    @ApiOperation("退出")
    @GetMapping("/logout")
    public Result<Object> logout(){

        // TODO 使用redis进行用户的状态登录和退出的记录
        // 使当前的JWT令牌失效 不管多长时间有效的JWT令牌
        // stats = 1 -> status = 0;
        log.info("退出");

        return Result.success();
    }
}
