package com.zone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageSearchWithDeleteStatusAndViewPowerStatus extends PageSearchDTO{
    private Integer userId; // 用户id
    private Integer deleteStatus; // 删除状态
    private Integer articleViewPower; // 查看权限
}
