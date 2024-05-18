package com.zone.vo;

import com.zone.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVO extends User {
    private String account; // 用户名
    private String avatarUrl; // 头像
    private Integer articleCount; // 文章数量
}
