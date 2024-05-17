package com.zone.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 文章
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {

    // 主键 自增
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id; // id
    private String title; // 标题
    private String content; // 内容
    private Integer creatorId; // 创建者id
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private LocalDateTime deleteTime; // 删除时间
    private Integer status; // 状态
    private Integer articleView; // 浏览数
    private Integer articleLike; // 点赞数
    private Integer articleCommentCount; // 评论数
    private Integer articleViewPower ; // 浏览权限
    private Integer deleteStatus; // 删除状态 1为删除 0为未删除 (回收站使用)

}
