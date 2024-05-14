package com.zone.controller;

import com.zone.dto.ArticlePublishDTO;
import com.zone.service.ArticleService;
import com.zone.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/publish")
    @ApiOperation("发布文章")
    @Transactional // 开启事务 确保事务一致性
    public Result publish(@RequestBody ArticlePublishDTO articlePublishDTO) {
        log.info("发布文章");
        articleService.publish(articlePublishDTO);

        return Result.success();
    }


}




