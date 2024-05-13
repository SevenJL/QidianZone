package com.zone.controller;

import com.zone.dto.LoginDTO;
import com.zone.restful.Result;
import com.zone.service.AdminService;
import com.zone.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
@Slf4j
@AllArgsConstructor // 构造函数注入Bean
public class LoginController {

    private final UserService userService;

    private final AdminService adminService;

    @GetMapping("/login")
    @ApiOperation("登录")
    public Result<Object> login(@RequestBody LoginDTO loginDTO){
        // 登录
        log.info("登录:{}",loginDTO);

        // 如果是用户登录
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

        // 如果是管理员登录
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
}
