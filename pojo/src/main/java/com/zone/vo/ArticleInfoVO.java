package com.zone.vo;

import com.zone.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleInfoVO extends Article implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String title; // 文章题目
    private String content; // 文章内容
    private List<String> categoryName; // 文章分类
    private List<String> tagName; // 文章标签
    private List<CommentVO> commentVOS; // 文章评论
    private Integer articleView; // 文章浏览量
    private Integer articleLike; // 文章点赞量
    private Integer articleCommentCount; // 文章评论量
    private Integer creatorId; // 文章创建者id

}
