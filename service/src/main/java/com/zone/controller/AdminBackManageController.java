package com.zone.controller;


import com.zone.dto.AdminDTO;
import com.zone.result.Result;
import com.zone.service.AdminService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "管理员管理")
@RequestMapping("/admin")
@RequiredArgsConstructor // 动态注入Bean注解
public class AdminBackManageController {

    private final AdminService adminService;

    /**
     * 修改管理员账号/密码
     */
    @PutMapping("/updateAdmin")
    public Result<Object> updateAdmin(@RequestBody AdminDTO adminDTO){
        adminService.updateAdmin(adminDTO);

        return Result.success("修改成功");
    }



}
