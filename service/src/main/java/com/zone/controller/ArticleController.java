package com.zone.controller;

import com.github.pagehelper.PageInfo;
import com.zone.dto.ArticleEditDTO;
import com.zone.dto.ArticlePublishDTO;
import com.zone.dto.PageSearchDTO;
import com.zone.entity.Article;
import com.zone.result.PageResult;
import com.zone.service.ArticleService;
import com.zone.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    /**
     * 发布文章
     */

    @PostMapping("/publish")
    @ApiOperation("发布文章")
    @Transactional
    public Result publish(@RequestBody ArticlePublishDTO articlePublishDTO) {
        log.info("发布文章");
        articleService.publish(articlePublishDTO);

        return Result.success("发布成功");
    }

    /**
     * 编辑文章
     */
    @PostMapping("/edit")
    @ApiOperation("编辑文章")
    public Result edit(@RequestBody ArticleEditDTO articleEditDTO) {
        log.info("编辑文章");
        articleService.edit(articleEditDTO);

        return Result.success("编辑成功");
    }

    /**
     * 删除文章
     * 逻辑删除
     * 只是将deleteStatus更改
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("(逻辑)删除文章")
    @Transactional
    public Result deleteById(@PathVariable("id") Integer id) {
        log.info("删除文章");
        articleService.delete(id);

        return Result.success("删除成功");
    }

    /**
     * 搜索文章 模糊搜索/文章分类/标签分类
     */
    @GetMapping("/search")
    @ApiOperation("根据文章题目进行模糊搜索")
    public Result<PageResult> search(@RequestBody PageSearchDTO pageSearchDTO) {
        log.info("根据文章题目进行模糊搜索");
        PageInfo<Article> articlePageInfo =   articleService.search(pageSearchDTO);
        long total = articlePageInfo.getTotal();
        List<Article> list = articlePageInfo.getList();
        PageResult result = PageResult.builder()
                .total(total)
                .records(list)
                .build();
        return Result.success(result);
    }







}




