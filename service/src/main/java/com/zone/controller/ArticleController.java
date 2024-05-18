package com.zone.controller;

import com.zone.dto.ArticleEditDTO;
import com.zone.dto.ArticlePublishDTO;
import com.zone.result.Result;
import com.zone.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/article")
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 发布文章
     */
    @PostMapping("/publish")
    @ApiOperation("发布文章")
    @Transactional // 事务
    public Result<Object> publish(@RequestBody ArticlePublishDTO articlePublishDTO) {
        log.info("发布文章");
        articleService.publish(articlePublishDTO);

        return Result.success("发布成功");

    }

    /**
     * 编辑文章
     */
    @PutMapping("/edit")
    @ApiOperation("编辑文章")
    @Transactional // 事务
    public Result<Object> edit(@RequestBody ArticleEditDTO articleEditDTO) {
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
    public Result<Object> deleteById(@PathVariable("id") Integer id) {
        log.info("删除文章");
        articleService.delete(id);

        return Result.success("删除成功");
    }












}




