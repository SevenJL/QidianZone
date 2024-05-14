package com.zone.controller;

import com.zone.dto.ArticleEditDTO;
import com.zone.dto.ArticlePublishDTO;
import com.zone.service.ArticleService;
import com.zone.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    //TODO 事务管理
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
    @ApiOperation("删除文章")
    public Result delete(@PathVariable("id") Integer id) {
        log.info("删除文章");
        articleService.delete(id);

        return Result.success("删除成功");
    }



}




