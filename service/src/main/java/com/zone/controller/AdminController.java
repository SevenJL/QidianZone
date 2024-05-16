package com.zone.controller;


import com.zone.dto.PageBean;
import com.zone.result.PageResult;
import com.zone.result.Result;
import com.zone.service.AdminService;
import com.zone.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Api(tags = "管理员管理")
@Slf4j
@RequiredArgsConstructor // 动态注入Bean注解
public class AdminController {

    private final UserService userService;

    private final AdminService adminService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/list")
    @ApiOperation("获取用户列表")
    public Result<PageResult> listUser(@RequestBody PageBean pageBean){
        PageResult users = userService.listUser(pageBean);

        return Result.success(users);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除用户")
    public Result<Object> deleteById(@PathVariable String id){
        adminService.deleteById(id);

        return Result.success("删除成功");
    }


}
