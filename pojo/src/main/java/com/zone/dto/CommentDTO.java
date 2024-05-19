package com.zone.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Integer id; // 评论id
    private Integer articleId; // 文章id
    private String content; // 评论内容
    private String creator; // 评论者
    private Integer parentId; // 父评论id
    private Integer rootId; // 根评论id
}
