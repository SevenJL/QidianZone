package com.zone.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zone.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论
 */

@Mapper
public interface CommentMapper extends BaseMapper<Comment>{

    /**
     * 通过文章id查询评论
     */
    List<Comment> selectByArticleId(@Param("articleId")Integer articleId);

    /**
     * 通过父id查询评论
     */
    List<Comment> selectByParentId(@Param("id") Integer id);

    /**
     * 通过子id查询评论
     */
    List<Comment> selectBySonId(@Param("sonId") Integer sonId);

    /**
     * 通过根id查询评论
     */
    void deleteByRootId(@Param("rootId") Integer rootId);

    /**
     * 通过评论id查找子评论
     */
    List<Integer> findSonCommentById(@Param("commentId") Integer commentId);

    /**
     * 通过评论id查找评论
     */
    Comment selectCommentById(@Param("commentId") Integer commentId);

    /**
     * 通过评论id删除子评论
     */
    void deleteSonCommentById(@Param("sonId") Integer sonId);

    /**
     * 插入子评论
     */
    void insertSonComment(Comment comment);

    /**
     * 插入评论
     */
    void insertComment(Comment comment);

    /**
     * 更新评论点赞数
     */
    void updateLike(@Param("commentId") Integer commentId);

}
