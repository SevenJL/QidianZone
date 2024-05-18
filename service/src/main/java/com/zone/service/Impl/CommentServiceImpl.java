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
                .updateTime(LocalDateTime.now()) // 更新时间
                .createTime(LocalDateTime.now()) // 评论时间
                .content(commentDTO.getContent()) // 评论内容
                .userId(BaseContext.getCurrentId()) // 评论用户id
                .articleId(commentDTO.getArticleId()) // 评论文章id
                .rootId(CommentConstant.DEFAULT_ROOT_ID) // 根评论id
                .parentId(CommentConstant.DEFAULT_PARENT_ID) // 父评论id
                .likeCount(CommentConstant.DEFAULT_LIKE_COUNT) // 点赞数
                .replyCount(CommentConstant.DEFAULT_REPLY_COUNT) // 回复数
                .build();

        // 2.插入数据
        commentMapper.insert(comment);
    }

    /**
     * 通过id删除评论
     */
    @Override
    public int deleteCommentById(Integer id) {
        // 1.只能删除自己的评论
//        if (!userService.getUserInfo(BaseContext.getCurrentId()).getId().equals(id)) {
//            // 只能删除自己的评论
//            return -1;
//        }

        // 2.先判断要删除的评论是否为根评论
        Comment comment = commentMapper.selectById(id);
        Integer rootId = comment.getParentId();
        if (rootId.equals(CommentConstant.DEFAULT_PARENT_ID)) {
            // 3.如果是根评论 直接根据根评论id删除所有的子评论
            commentMapper.deleteByRootId(comment.getId());
        }

        // 4.如果不是根评论 则需要根据评论id删除评论
        deleteAllSonComments(id);

        return commentMapper.deleteById(id);
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
        List<Comment> comments = commentMapper.selectByParentId(id);
        ArrayList<CommentVO> commentVOS = new ArrayList<>();
        comments.forEach(comment -> {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            commentVOS.add(commentVO);
        });
        return commentVOS;
    }


    private void deleteAllSonComments(Integer commentId){
        // 1.先查询该评论是否有子评论
        List<Comment> sonComments = commentMapper.selectBySonId(commentId);
        if (sonComments.isEmpty()) {
            // 1.如果没有子评论 直接删除
            commentMapper.deleteById(commentId);
            return;
        }
        // 2.如果有先去寻找给子评论的子评论
        sonComments.forEach(sonComment -> {
            // 2.2再删除当前子评论
            commentMapper.deleteById(sonComment.getId());
            // 2.1.先递归删除子评论的子评论的.....
            deleteAllSonComments(sonComment.getId());
        });
    }






    /**
     * 循环查询子评论
     * 废弃该方法
     * 由于子评论可能有多个
     * 所以子评论的Id也必须为一个集合
     * 逻辑过于复杂 对数据库的请求可能会过载
     */
    //
//    @Deprecated
//    private List<CommentVO> selectSonComment(List<Comment> comments,
//                                             List<CommentVO> commentVOS) {
//        CommentVO commentVO = new CommentVO();
//        comments.forEach(comment -> {
//            // 找到那些评论的子评论
//            Integer sonId = comment.getSonId();
//            if (sonId != null) {
//                ArrayList<CommentVO> vos = new ArrayList<>();
//                // 子评论集合
//                List<Comment> sonCommentList = commentMapper.selectBySonId(sonId);
//                // 递归查询 子评论的子评论
//                // sonComments是子评论的子评论集合
//                List<CommentVO> sonComments = selectSonComment(sonCommentList, vos);
//                // 查询字评论完毕 把子评论集合赋给父评论
//                commentVO.setCommentVOS(sonComments);
//            }
//            // 没有子评论 或 已经把 子评等查询完了
//            BeanUtils.copyProperties(comment, commentVO);
//        });
//        commentVOS.add(commentVO);
//        return commentVOS;
//    }
}
