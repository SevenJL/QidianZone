package com.zone.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleRecycleBinVO {
    private Integer id; // 文章id
    private String title; // 标题
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private Integer articleView; // 浏览量
    private Integer categoryId; // 类型ID
}
