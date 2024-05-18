package com.zone.controller;


import com.zone.constant.JwtClaimsConstant;
import com.zone.dto.AdminDTO;
import com.zone.dto.LoginDTO;
import com.zone.dto.PageBean;
import com.zone.properties.JwtProperties;
import com.zone.result.PageResult;
import com.zone.result.Result;
import com.zone.service.AdminService;
import com.zone.utils.JwtUtil;
import com.zone.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "管理员管理")
@RequestMapping("/admin")
@RequiredArgsConstructor // 动态注入Bean注解
public class AdminBackManageController {

    private final AdminService adminService;

    private final JwtProperties jwtProperties;

    @GetMapping("/login")
    @ApiOperation("登录")
    public Result<Object> login(@RequestBody LoginDTO loginDTO) {
        // 1.登录
        log.info("登录:{}", loginDTO);
        //TODO 输入的密码进行MD5加密
        Integer id = -1;

        // 2.管理员登录
        id = adminService.login(loginDTO);
        if (id == null || id == -1) {
            return Result.error("登录失败");
        }
        // 3.登录成功
        log.info("管理员:{}登录成功", loginDTO.getName());
        // 生成JWT令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ADMIN_ID, id);

        // 生成JWT令牌
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(), claims);
        String openid = "openid";

        // 封装VO
        LoginVO userLoginVO = LoginVO.builder()
                .id(id)
                .openid(openid)
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }

    /**
     * 修改管理员账号/密码
     */
    @PutMapping("/updateAdmin")
    public Result<Object> updateAdmin(@RequestBody AdminDTO adminDTO) {
        adminService.updateAdmin(adminDTO);

        return Result.success("修改成功");
    }

    /**
     * 分页查询用户列表
     */
    @GetMapping("/list")
    @ApiOperation("获取用户列表")
    public Result<PageResult> listUser(@RequestBody PageBean pageBean) {
        PageResult users = adminService.listUser(pageBean);

        return Result.success(users);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除用户")
    @Transactional // 事务管理
    public Result<Object> deleteById(@PathVariable String id) {
        adminService.deleteById(id);

        return Result.success("删除成功");
    }
}
