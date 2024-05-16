package com.zone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageBean {

    private Integer pageNum; // 页码
    private Integer pageSize; // 每页数量

}
