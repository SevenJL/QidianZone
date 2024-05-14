package com.zone.dto;

import com.zone.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePublishDTO {
    private Integer categoryId; // 分类id
    private String title; // 文章标题
    private String content; // 文章内容
    private Integer articleViewPower; // 文章阅读权限
}
