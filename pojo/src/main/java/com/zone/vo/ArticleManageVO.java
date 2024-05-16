package com.zone.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ArticleManageVO {
    private Integer id; // 文章id
    private String title; // 文章题目
    private String creator; // 创建者
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private Integer articleView; // 浏览量
    private List<String> categoryName; // 分类名称

}
