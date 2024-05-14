package com.zone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 评论实体
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer id; // id
    private String content; // 评论内容
    private Integer articleId; // 评论文章id
    private Integer userId; // 评论用户id
    private Integer commentId; // 评论id
    private LocalDateTime createTime; // 评论时间
    private LocalDateTime updateTime; // 更新时间
    private Integer status; // 评论状态
}
