package com.zone.controller.common;


import com.zone.dto.PageSearchDTO;
import com.zone.entity.NewArticle;
import com.zone.result.PageResult;
import com.zone.result.Result;
import com.zone.service.ArticleService;
import com.zone.service.CommentService;
import com.zone.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公共控制器
 */


@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommonController {

    private final ArticleService articleService;

    private final CommentService commentService;


    /**
     * 搜索文章 <br>
     * 模糊搜索 <br>
     * 文章分类 <br>
     * 标签分类
     */
    @GetMapping("/search")
    public Result<PageResult> search(@RequestBody PageSearchDTO pageSearchDTO) {
        log.info("根据 文章题目/文章分类/标签分类 进行模糊搜索");
        // 获取分页信息
        PageResult ar = articleService.search(pageSearchDTO);

        // 封装分页信息
        long total = ar.getTotal();
        List records = ar.getRecords();

        // 返回
        return Result.success(new PageResult(total, records));
    }

    /**
     * 显示最新文章
     */
    @GetMapping("/listLatestArticle")
    public Result<List<NewArticle>> listNew() {
        log.info("显示最新文章");
        // TODO 根据页面需要 再去进行修改 是否需要返回评论
        //  如果需要只需要调用articleCommentMapper中的getCommentByArticleId方法即可获得
        List<NewArticle> articles = articleService.listNew();

        return Result.success(articles);
    }

    /**
     * 查询文章评论
     */
    @GetMapping("/findArticleComment")
    public Result<Object> findComment(@RequestParam("articleId") Integer articleId) {
        log.info("查询评论");

        // 先查询文章的评论
        List<CommentVO> comments = commentService.selectByArticleId(articleId);

        return Result.success(comments);
    }

    /**
     * 查看评论的回复
     */
    @GetMapping("/findCommentReply")
    public Result<Object> findCommentReply(@RequestParam("id") Integer id) {
        log.info("查看评论的回复");
        List<CommentVO> commentVOS = commentService.selectByParentId(id);
        return Result.success(commentVOS);
    }




}
