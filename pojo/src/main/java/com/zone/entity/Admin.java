package com.zone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 管理员
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private Integer id; // 管理员id
    private String name; // 管理员名
    private LocalDateTime createTime; // 管理员创建时间
    private LocalDateTime deleteTime; // 管理员删除时间
    private String password; // 管理员密码
    private String email; // 管理员邮箱
    private String status; // 管理员状态
    private String deleteStatus; // 管理员删除状态
    private String avatarUrl; // 管理员头像


}
