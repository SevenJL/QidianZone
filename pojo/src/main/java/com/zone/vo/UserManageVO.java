package com.zone.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户管理显示的User数据
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserManageVO {
    private Integer id;
    private String account;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
