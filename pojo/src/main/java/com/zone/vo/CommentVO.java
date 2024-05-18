package com.zone.vo;

import com.zone.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 评论视图对象
 */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO extends Comment implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // 序列化id
    private List<CommentVO> commentVOS; // 子评论
}
