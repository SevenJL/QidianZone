package com.zone.controller;


import com.zone.constant.JwtClaimsConstant;
import com.zone.dto.LoginDTO;
import com.zone.dto.RegisterDTO;
import com.zone.properties.JwtProperties;
import com.zone.result.Result;
import com.zone.service.AdminService;
import com.zone.service.UserService;
import com.zone.utils.JwtUtil;
import com.zone.vo.LoginVO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * 用户登录注册注销
 */


@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginRegisterLogoutController {

    private final UserService userService;

    private final AdminService adminService;

    private final JwtProperties jwtProperties;

    @GetMapping("/login")
    @ApiOperation("登录")
    public Result<Object> login(@RequestBody LoginDTO loginDTO){
        // 1.登录
        log.info("登录:{}",loginDTO);
        //TODO 输入的密码进行MD5加密

        Integer id = -1 ;

        // 2.如果是用户登录
        if (loginDTO.getPower().equals("user")){
            // 获得用户id
            id = userService.login(loginDTO);
            if (id == null || id == -1){
                return Result.error("登录失败");
            }
            // 登录成功
            log.info("用户:{}登录成功",loginDTO.getName());
        }

        // 3.如果是管理员登录
        if (loginDTO.getPower().equals("admin")){
            // 获得用户id
            id = adminService.login(loginDTO);
            if (id == null || id == -1){
                return Result.error("登录失败");
            }
            // 登录成功
            log.info("管理员:{}登录成功",loginDTO.getName());
        }
        if (id == -1){
            return Result.error("登录失败");
        }
        log.info("用户id:{}",id);

        // 生成JWT令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.DEFAULT,id);

        // 生成JWT令牌
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        String openid = "openid";

        // 封装VO
        LoginVO userLoginVO = LoginVO.builder()
                .id(id)
                .openid(openid)
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }

    @ApiOperation("退出")
    @GetMapping("/logout")
    public Result<Object> logout(){

        // TODO 使用redis进行用户的状态登录和退出的记录
        // 使当前的JWT令牌失效 不管多长时间有效的JWT令牌
        log.info("退出");

        return Result.success();
    }

    @GetMapping("/register")
    @ApiOperation("注册")
    @Transactional
    public Result<String> register(@RequestBody RegisterDTO registerDTO){

        // 1.注册
        log.info("注册用户:{}",registerDTO);

        //TODO 使用MD5加密密码 再传入数据库

        // 2.将用户注册的数据传给数据库
        Integer update =  userService.register(registerDTO.getPassword(),registerDTO.getName(),registerDTO.getEmail());

        // 3.判断是否注册成功
        if(update == -1){
            return Result.error("注册失败");
        }

        // 4.数据传入数据库后返回成功
        log.info("用户:{}注册成功",registerDTO.getName());
        return Result.success("注册成功");

    }
}
