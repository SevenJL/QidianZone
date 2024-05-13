package com.zone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 回收站
 */

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RecycleBin {
    private Integer id; // id
    private String title; // 标题
    private String content; // 内容
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private Integer status; // 状态
    private List<User> creator; // 创建者
    private Integer categoryId; // 分类id
    private String categoryName; // 分类名
    private List<String> comment; // 评论
    private Integer articleLike; // 点赞数
    private Integer articleView; // 浏览数
    private Integer articleCommentCount; // 评论数
    private ArticleViewPower articleViewPower ; // 浏览权限

}
