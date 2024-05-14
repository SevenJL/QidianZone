package com.zone.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 前端文章显示VO
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVO implements Serializable {
    private String articleId; // 文章id
    private String categoryName; // 分类名
    private String author; // 作者
    private String title; // 文章标题
    private String content; // 文章内容
    private Integer articleView; // 浏览量
    private Integer articleCommentCount; // 评论数
    private Integer articleLike; // 点赞数

}
