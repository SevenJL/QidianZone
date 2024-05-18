package com.zone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 评论实体
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id; // id
    private Integer articleId; // 评论文章id
    private Integer parentId; // 父评论id
    private Integer rootId; // 根评论id
    private Integer userId; // 评论用户id

    private LocalDateTime createTime; // 评论时间
    private LocalDateTime updateTime; // 更新时间

    private String content; // 评论内容
    private Integer likeCount; // 点赞数
    private Integer replyCount; // 回复数

}
