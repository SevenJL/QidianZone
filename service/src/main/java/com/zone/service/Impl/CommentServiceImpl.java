package com.zone.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        comment.setCreateTime(LocalDateTime.now());
        comment.setArticleId(commentDTO.getArticleId());
        BeanUtils.copyProperties(commentDTO, comment);
        // 2.插入数据
        commentMapper.insert(comment);
    }

    /**
     * 通过id删除评论
     */
    @Override
    public int deleteById(Integer id) {
        // 1.删除评论
        if (!userService.getUserInfo(BaseContext.getCurrentId()).getId().equals(id)) {
            // 只能删除自己的评论
            return -1;
        }

        // 2.删除评论
        return commentMapper.deleteById(id);
    }

    /**
     * 通过文章id查询评论
     */
    @Override
    public List<CommentVO> selectByArticleId(Integer articleId) {
        // 1.先查询这篇文章的评论
        List<Comment> comments = commentMapper.selectByArticleId(articleId);
        // 再根据这些文章的评论去反复查询是否有子评论
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
        List<Comment> comments = commentMapper.selectByParentId(id);
        ArrayList<CommentVO> commentVOS = new ArrayList<>();
        comments.forEach(comment -> {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            commentVOS.add(commentVO);
        });
        return commentVOS;
    }

    /**
     * 循环查询子评论
     */
    // 废弃该方法
    // 由于子评论可能有多个
    // 所以子评论的Id也必须为一个集合 来保存多个子id
    // 所以该方法废弃
    @Deprecated
    private List<CommentVO> selectSonComment(List<Comment> comments,
                                             List<CommentVO> commentVOS) {
        CommentVO commentVO = new CommentVO();
        comments.forEach(comment -> {
            // 找到那些评论的子评论
            Integer sonId = comment.getSonId();
            if (sonId != null) {
                ArrayList<CommentVO> vos = new ArrayList<>();
                // 子评论集合
                List<Comment> sonCommentList = commentMapper.selectBySonId(sonId);
                // 递归查询 子评论的子评论
                // sonComments是子评论的子评论集合
                List<CommentVO> sonComments = selectSonComment(sonCommentList, vos);
                // 查询字评论完毕 把子评论集合赋给父评论
                commentVO.setCommentVOS(sonComments);
            }
            // 没有子评论 或 已经把 子评等查询完了
            BeanUtils.copyProperties(comment, commentVO);
        });
        commentVOS.add(commentVO);
        return commentVOS;
    }
}
