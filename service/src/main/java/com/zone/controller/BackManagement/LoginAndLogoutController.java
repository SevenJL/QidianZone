package com.zone.controller.BackManagement;

import com.zone.constant.MessageConstant;
import com.zone.entity.User;
import com.zone.result.Result;
import com.zone.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 登录和退出
 */

@Slf4j
@RestController
@RequestMapping
@Api(tags = "登录端")
public class LoginAndLogoutController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    @ApiOperation("登录")
    public Result<Object> login(@RequestBody User user){
        // 登录
        log.info("登录用户:{}",user);

        // TODO 验证码在前端进行展示

        // 获得密码和账户名
        String password = user.getPassword();
        String name = user.getName();

        // 传入数据库 进行对比
        Integer userId = userService.login(password,name);
        if (userId == null && userId == -1){
            return Result.error(MessageConstant.PASSWORD_ERROR); // 密码错误
        }

        // 登录成功
        log.info("用户:{}登录成功",name);
        return Result.success("登录成功");
    }


    @ApiOperation("退出")
    @PostMapping("/logout/{id}")
    public Result logout( @PathVariable("id") Integer id){
        // TODO 使用redis进行用户的状态登录和退出的记录
        // 根据jwt令牌解析用户id 然后BaseContext.getCurrentId() 获得用户id
        // 从而更新状态
        // stats = 1 -> status = 0;

        // 这里先使用数据库进行保存状态数据
        userService.logout(id);
        log.info("退出成功");

        return Result.success("退出成功");
    }

}
