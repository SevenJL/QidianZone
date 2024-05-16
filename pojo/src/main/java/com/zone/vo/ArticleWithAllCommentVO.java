package com.zone.vo;


import com.zone.entity.Article;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 最新文章的VO类
 */

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ArticleWithAllCommentVO extends Article {
    private List<String> allComment; // 评论
}

