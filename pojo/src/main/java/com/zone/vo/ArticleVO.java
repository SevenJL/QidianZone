package com.zone.vo;


import com.zone.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 前端文章显示VO
 */

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ArticleVO extends Article {
    private List<String> categoryName;
}
