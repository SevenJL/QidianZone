package com.zone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageSearchWithUserIdAndDeleteStatus extends PageSearchDTO{
    private Integer userId; // 用户id
    private Integer deleteStatus; // 删除状态
}
