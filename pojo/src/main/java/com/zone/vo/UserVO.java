package com.zone.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private String name; // 用户名
    private String avatarUrl; // 头像
    private Integer articleCount; // 文章数量
}
