package com.zone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageSearchWithDeletePower extends PageSearchDTO{
    private Integer userId; // 用户id
    private Integer deleteStatus; // 删除状态
    private Integer articleViewPower; // 查看权限
    private Integer pageNum; // 页码
    private Integer pageSize; // 每页数量
    private String title; // 标题
    private Integer categoryId; // 分类id
    private Integer tagId; // 标签id
}
