package com.zone.controller;

import com.zone.context.BaseContext;
import com.zone.dto.UserUpdatePasswordDTO;
import com.zone.entity.User;
import com.zone.result.Result;
import com.zone.service.UserService;
import com.zone.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 修改密码
     * @param userUpdatePasswordDTO 修改密码的DTO对象
     */
    @GetMapping("/updatePassword")
    @ApiOperation("修改密码")
    public Result<Object> updatePassword(@RequestBody UserUpdatePasswordDTO userUpdatePasswordDTO){
        log.info("修改密码:{}",userUpdatePasswordDTO);
        // TODO 需要对修改的密码进行MD5加密

        // 1.传入DTO对象
        Integer id =  userService.update(userUpdatePasswordDTO);

        // 2.如果用户不存在
        if(id == -1){
            return Result.error("用户不存在");
        }

        log.info("修改密码成功");
        return Result.success("修改密码成功");
    }

    /**
     * 修改昵称
     *
     */

    @GetMapping("/updateNickname")
    @ApiOperation("修改昵称")
    public Result<Object> updateNickname(@RequestParam("id") Integer id,
                                         @RequestParam("nickname") String nickName){
        log.info("修改昵称:{},id:{}",nickName,id);

        // 传入DTO对象
        userService.updateNickName(id,nickName);

        log.info("修改昵称成功");
        return Result.success("修改昵称成功");
    }

    /**
     * 修改头像
     */

    @GetMapping("/updateAvatar")
    @ApiOperation("修改头像")
    public Result<Object> updateAvatar(@RequestParam("id") Integer id,
                                       @RequestParam("avatarUrl") String avatarUrl){
        log.info("修改头像:{},id:{}",avatarUrl,id);

        // 传入DTO对象
        userService.updateAvatar(id,avatarUrl);

        log.info("修改头像成功");
        return Result.success("修改头像成功");
    }


    /**
     * 获取用户最基本信息
     *
     */

    @GetMapping("/getUserInfo")
    @ApiOperation("获取用户最基本信息")
    public Result<UserVO> getUserInfo(){
        log.info("获取用户信息");

        // 传入DTO对象
        // 利用LocalThread获取用户信息
        Integer id = BaseContext.getCurrentId();
        log.info("当前用户id:{}",id);
        User user = userService.getUserInfo(id);

        // 将获取的User 对象 拷贝到 UserVO
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        // 返回用户基本信息
        log.info("获取用户信息成功");
        return Result.success(userVO);
    }

}
