package com.zone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTag implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id; // 主键
    private Integer articleId; // 文章id
    private Integer tagId; // 标签id

}
