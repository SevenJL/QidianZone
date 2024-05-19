package com.zone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分类实体类
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Category  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id; // 分类id
    private String name; // 分类名称
}
