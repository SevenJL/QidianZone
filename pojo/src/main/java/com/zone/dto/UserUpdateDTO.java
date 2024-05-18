package com.zone.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    private Integer id; // 用户id
    private String nickName; // 昵称
    private String avatarUrl; // 头像
    private String password; // 密码
    private String account; // 用户名
}
