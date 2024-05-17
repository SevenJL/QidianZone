package com.zone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 标签
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Tag implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id; // 标签id
    private String name; // 标签名
}
