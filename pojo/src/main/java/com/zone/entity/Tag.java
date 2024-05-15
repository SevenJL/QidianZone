package com.zone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Tag {
    private Integer id; // 标签id
    private String name; // 标签名
}
