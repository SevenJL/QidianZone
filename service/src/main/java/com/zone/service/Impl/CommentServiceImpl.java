package com.zone.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zone.constant.CommentConstant;
import com.zone.context.BaseContext;
import com.zone.dto.CommentDTO;
import com.zone.entity.Comment;
import com.zone.mapper.CommentMapper;
import com.zone.service.CommentService;
import com.zone.vo.CommentVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论服务实现
 */


@Service
@Slf4j
@AllArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final CommentMapper commentMapper;

    private final UserServiceImpl userService;

    /**
     * 插入评论
     */
    @Override
    public void insert(CommentDTO commentDTO) {
        // 1.传入DTO对象
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        Comment.builder()
                .updateTime(LocalDateTime.now())                    // 更新时间
                .createTime(LocalDateTime.now())                    // 评论时间
                .content(commentDTO.getContent())                   // 评论内容
                .userId(BaseContext.getCurrentId())                 // 评论用户id
                .articleId(commentDTO.getArticleId())               // 评论文章id
                .rootId(CommentConstant.DEFAULT_ROOT_ID)            // 根评论id
                .parentId(CommentConstant.DEFAULT_PARENT_ID)        // 父评论id
                .likeCount(CommentConstant.DEFAULT_LIKE_COUNT)      // 点赞数
                .replyCount(CommentConstant.DEFAULT_REPLY_COUNT)    // 回复数
                .build();

        // 2.插入数据
        commentMapper.insert(comment);
    }

    @Override
    public void insertReply(CommentDTO commentDTO) {
        // 1.传入DTO对象
        Comment comment = Comment.builder()
                .updateTime(LocalDateTime.now())                    // 更新时间
                .createTime(LocalDateTime.now())                    // 评论时间
                .content(commentDTO.getContent())                   // 评论内容
                .userId(BaseContext.getCurrentId())                 // 评论用户id
                .articleId(commentDTO.getArticleId())               // 评论文章id
                .rootId(commentDTO.getRootId())                     // 根评论id
                .parentId(commentDTO.getParentId())                 // 父评论id
                .likeCount(CommentConstant.DEFAULT_LIKE_COUNT)      // 点赞数
                .replyCount(CommentConstant.DEFAULT_REPLY_COUNT)    // 回复数
                .build();
        log.info("插入的评论为:{}", comment.toString());

        // 2.插入数据
        commentMapper.insertComment(comment);
        Integer sonId = comment.getId();
        log.info("插入的子评论id为:{}", sonId);

        // 3.关联父评论
        commentMapper.insertSonComment(Comment.builder()
                .id(sonId)
                .parentId(commentDTO.getParentId())
                .build());
    }

    /**
     * 通过文章id查询评论
     */
    @Override
    public List<CommentVO> selectByArticleId(Integer articleId) {
        // 1.先查询这篇文章的评论
        List<Comment> comments = commentMapper.selectByArticleId(articleId);

        // 2.将评论转换为VO对象
        List<CommentVO> commentVOS = new ArrayList<>();
        comments.forEach(comment -> {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            commentVOS.add(commentVO);
        });

        return commentVOS;
    }

    /**
     * 通过父id查询评论
     */
    @Override
    public List<CommentVO> selectByParentId(Integer id) {
        // 1.查询父评论
        List<Comment> comments = commentMapper.selectByParentId(id);
        ArrayList<CommentVO> commentVOS = new ArrayList<>();
        // 2.将评论转换为VO对象
        comments.forEach(comment -> {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            commentVOS.add(commentVO);
        });
        // 3.返回VO对象
        return commentVOS;
    }


    /**
     * 通过id删除评论
     */
    @Override
    public int deleteCommentById(Integer id) {
        // 1.只能删除自己的评论
        if (!userService.getUserInfo(BaseContext.getCurrentId()).getId().equals(id)) {
            // 只能删除自己的评论
            return -1;
        }

        // 2.先判断要删除的评论 是否为根评论
        Comment comment = commentMapper.selectCommentById(id);
        Integer rootId = comment.getParentId();

        // 3.说明为根评论 直接根据 根评论id 删除所有的子评论
        if (rootId.equals(CommentConstant.DEFAULT_PARENT_ID)) {
            // 删除 根评论
            commentMapper.deleteByRootId(id);

            // 根评论 下的子评论 也需要去删除
            // 查询 根评论下的 所有子评论
            List<Integer> sonCommentById =
                    commentMapper.findSonCommentById(comment.getId());
            sonCommentById.forEach(this::deleteAllSonComments);
        }

        // 4.说明不是根评论 需要查询子评论 并删除
        if (!rootId.equals(CommentConstant.DEFAULT_ROOT_ID)) {
            deleteAllSonComments(comment.getId());
        }

        return commentMapper.deleteById(id);
    }

    /**
     * 递归删除所有子评论
     */
    private void deleteAllSonComments(Integer commentId) {
        // 1.先查询该评论是否有子评论
        // 查询子评论
        List<Integer> sonCommentIds = commentMapper.findSonCommentById(commentId);
        if (sonCommentIds.isEmpty()) {
            // 2.说明没有子评论 直接删除
            commentMapper.deleteById(commentId);
            commentMapper.deleteSonCommentById(commentId);
            return;

        }
        // 3.如果有 先去寻找子评论的子评论
        sonCommentIds.forEach(this::deleteAllSonComments);
    }
}
