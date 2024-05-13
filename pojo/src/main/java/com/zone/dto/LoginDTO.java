package com.zone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录DTO
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LoginDTO {
    private String name;
    private String password;
    private String power;
}
