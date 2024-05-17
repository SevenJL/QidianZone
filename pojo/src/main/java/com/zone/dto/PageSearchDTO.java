package com.zone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页参数
 */


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageSearchDTO {
    private Integer pageNum; // 页码
    private Integer pageSize; // 每页数量
    private String title; // 标题
    private Integer deleteStatus; // 删除状态
    private Integer categoryId; // 分类id
    private Integer tagId; // 标签id
    private Integer userId; // 用户id
}
