package com.zone.controller.BackManagement;

import com.zone.entity.User;
import com.zone.restful.Result;
import com.zone.service.AdminService;
import com.zone.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin")
@Api(tags = "")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @ApiOperation("查询所有的用户信息")
    @GetMapping("/list")
    public Result<Object> list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {

        List<User> list = adminService.listAllUsers();
        return Result.success();
    }



}
