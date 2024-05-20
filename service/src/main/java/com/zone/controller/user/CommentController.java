package com.zone.controller.user;


import com.zone.dto.CommentDTO;
import com.zone.result.Result;
import com.zone.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * 评论控制器
 */

@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping("/user/comment")
public class CommentController {

    private final CommentService commentService;

    /**
     * 添加根评论
     */
    @PostMapping("/addComment")
    @Transactional
    public Result<Object> addComment(@RequestBody CommentDTO commentDTO) {
        log.info("添加评论:{}",commentDTO);
        commentService.insert(commentDTO);

        log.info("添加评论成功");
        return Result.success("添加评论成功");
    }

    /**
     * 添加子评论
     */
    @PostMapping("/addReply")
    @Transactional
    public Result<Object> addReply(@RequestBody CommentDTO commentDTO) {
        log.info("添加子评论:{}",commentDTO);
        commentService.insertReply(commentDTO);

        log.info("添加子评论成功");
        return Result.success("添加子评论成功");
    }


    /**
     * 删除评论 <br>
     * 只有当前用户才能删除自己的评论
     */
    @DeleteMapping("/deleteComment")
    @Transactional
    public Result<Object> deleteComment(@RequestParam("commentId") Integer commentId) {
        log.info("删除评论id:{}",commentId);

        int status = commentService.deleteCommentById(commentId);
        if (status == -1) {
            return Result.error("只能删除自己的评论");
        }

        return Result.success("删除评论成功");
    }

    /**
     * 给评论点赞<br>
     * 更新评论的点赞数
     */
    @PutMapping("/likeComment")
    public Result<Object> likeComment(@RequestParam("commentId") Integer commentId) {
        log.info("点赞评论id:{}",commentId);

        commentService.updateLike(commentId);

        return Result.success("点赞成功");
    }
}
