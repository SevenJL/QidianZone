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
    private Integer userId; // 用户id
    private String password; // 用户密码
    private String name; // 用户名
    private String nickname; // 用户昵称
    private String email; // 用户邮箱
    private String power; // 用户权限
    private LocalDateTime createTime; // 用户创建时间
    private LocalDateTime updateTime; // 用户更新时间
    private Integer deleteStatus;
    private LocalDateTime deleteTime;
    private Integer status; // 用户状态
    private Integer articleCount; // 用户文章数量
    private String avatarUrl; // 用户头像
    private Integer articleLike;// 用户文章点赞数量

}
