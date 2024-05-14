package com.zone.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 文章
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Integer id; // id
    private String title; // 标题
    private String content; // 内容
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private LocalDateTime deleteTime; // 删除时间
    private Integer status; // 状态
    private String creator; // 创建者
    private Integer categoryId; // 分类id
    private Integer commentId; // 评论
    private Integer articleLike; // 点赞数
    private Integer articleView; // 浏览数
    private Integer articleCommentCount; // 评论数
    private Integer articleViewPower ; // 浏览权限
    private Integer deleteStatus; // 删除状态 1为删除 0为未删除 (回收站使用)




}
