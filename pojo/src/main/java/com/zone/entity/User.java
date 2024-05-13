package com.zone.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer user_id; // 用户id
    private String password; // 用户密码
    private String name; // 用户名
    private String email; // 用户邮箱
    private String power; // 用户权限
    private LocalDateTime createTime; // 用户创建时间
    private LocalDateTime updateTime; // 用户更新时间
    private Integer status; // 用户状态
    private String deleteTime; // 用户删除时间
    private Integer deleteStatus; // 用户删除状态
}
