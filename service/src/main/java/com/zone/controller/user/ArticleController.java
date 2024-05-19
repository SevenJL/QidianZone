package com.zone.controller.user;

import com.zone.dto.ArticleEditDTO;
import com.zone.dto.ArticlePublishDTO;
import com.zone.result.Result;
import com.zone.service.ArticleService;
import com.zone.vo.ArticleInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * 文章控制器
 */

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
    public Result<Object> deleteById(@PathVariable("id") Integer id) {
        log.info("删除文章");
        articleService.delete(id);

        return Result.success("删除成功");
    }



    /**
     * 查看文章 <br>
     * 并且增加文章的浏览量
     */
    @PutMapping("/checkOutArticle")
    public Result<Object> checkOutArticle(@RequestParam("articleId") Integer articleId) {
        log.info("查询的文章id:{}",articleId);
        ArticleInfoVO articleInfoVO = articleService.checkOutArticle(articleId);

        return Result.success(articleInfoVO);
    }

    /**
     * 点赞  <br>
     * 也就是更新点赞数
     */
    @PutMapping("/updateArticleLike")
    public Result<Object> updateArticleLike(@RequestParam("articleId") Integer articleId) {
        log.info("点赞的文章id:{}",articleId);
        articleService.updateArticleLike(articleId);

        return Result.success("点赞成功");
    }
}




