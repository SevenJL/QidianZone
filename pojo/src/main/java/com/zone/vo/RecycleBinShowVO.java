package com.zone.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RecycleBinShowVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id; // 文章id
    private String title; // 文章题目
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private Integer articleView; // 浏览量
    private List<String> categoryName; // 分类名称
    private List<String> tagName; // 标签名称
}
