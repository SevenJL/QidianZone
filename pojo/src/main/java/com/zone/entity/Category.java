package com.zone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类实体类
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Category {
    private Integer id; // 分类id
    private String name; // 分类名称
}
