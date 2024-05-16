package com.zone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTag {
    private Integer id; // 主键
    private Integer articleId; // 文章id
    private Integer tagId; // 标签id

}
