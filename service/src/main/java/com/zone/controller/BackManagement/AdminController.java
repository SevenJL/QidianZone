package com.zone.controller.BackManagement;

import com.zone.result.PageResult;
import com.zone.result.Result;
import com.zone.service.AdminService;
import com.zone.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
        PageResult users = adminService.list(page, pageSize);
        return Result.success(users);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable("id") Integer id,@RequestParam("status") Integer status) {
        Boolean delete = userService.deleteById(id, status);
        return Result.success();
    }



}
