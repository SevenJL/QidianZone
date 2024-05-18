package com.zone.controller;


import com.zone.dto.PageSearchDTO;
import com.zone.entity.NewArticle;
import com.zone.result.PageResult;
import com.zone.result.Result;
import com.zone.service.ArticleService;
import com.zone.vo.ArticleVO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommonController {
    private final ArticleService articleService;


    /**
     * 搜索文章 模糊搜索/文章分类/标签分类
     */
    @GetMapping("/search")
    @ApiOperation("根据文章题目进行模糊搜索")
    public Result<PageResult> search(@RequestBody PageSearchDTO pageSearchDTO) {
        log.info("根据文章题目进行模糊搜索");
        // 获取分页信息
        PageResult ar = articleService.search(pageSearchDTO);

        // 封装分页信息
        long total = ar.getTotal();
        List<ArticleVO> records = ar.getRecords();

        // 返回
        return Result.success(new PageResult(total, records));
    }

    /**
     * 显示最新文章
     */
    @GetMapping("/listNew")
    @ApiOperation("显示最新文章")
    public Result<List<NewArticle>> listNew() {
        log.info("显示最新文章");
        // TODO 根据页面需要 再去进行修改 是否需要返回评论
        //  如果需要只需要调用articleCommentMapper中的getCommentByArticleId方法即可获得
        List<NewArticle> articles = articleService.listNew();

        return Result.success(articles);
    }
}
