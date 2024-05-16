package com.zone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 编辑文章的DTO
 */


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ArticleEditDTO {
    private Integer id; // 文章id
    private String title; // 文章标题
    private String content; // 文章内容
    private List<Integer> categoryId; // 文章分类id
    private Integer articleViewPower; // 文章阅读权限
    private List<Integer> tagId; // 文章标签
}
