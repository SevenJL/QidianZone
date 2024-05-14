package com.zone.constant;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleAboutConstant {
    // 默认点赞数
    public static final Integer DEFAULT_ARTICLE_LIKE_VALUE = 0;
    // 默认评论数
    public static final Integer DEFAULT_ARTICLE_COMMENT_COUNT = 0;
    // 默认浏览数
    public static final Integer DEFAULT_ARTICLE_VIEW = 0;
    // 默认删除状态
    public static final String DEFAULT_DELETE_STATUS = "0";
    // 默认文章状态
    public static final String DEFAULT_STATUS = "0";
    // 默认创建者
    public static final String DEFAULT_CREATOR = "admin";
    // 默认访问权限
    public static final Integer DEFAULT_ARTICLE_VIEW_POWER = 0;
    // 默认删除时间 1970-01-01 00:00:00
    public static final LocalDateTime DEFAULT_ARTICLE_DELETE_TIME = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
    // 默认评论id
    public static final Integer DEFAULT_COMMENT_ID = 0;

}
