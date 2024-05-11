package com.zone.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId; // 用户id
    private String password; // 用户密码
    private String userName; // 用户名
    private String email; // 用户邮箱
    private String power; // 用户权限
    private String createTime; // 用户创建时间
    private String updateTime; // 用户更新时间
    private String status; // 用户状态
}
