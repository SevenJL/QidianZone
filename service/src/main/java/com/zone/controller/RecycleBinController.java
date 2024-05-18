package com.zone.controller;

import com.zone.dto.PageSearchDTO;
import com.zone.result.PageResult;
import com.zone.result.Result;
import com.zone.service.ArticleService;
import com.zone.service.RecycleBinService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 回收站控制器
 */


@Slf4j
@RestController
@RequestMapping("/user/bin")
@AllArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;

    private final ArticleService articleService;


    /**
     *  单个删除文章 和 批量删除文章  <br>
     *  删除单个 也是 删除批量 的一种 <br>
     *  所以直接写 删除批量文章 的方法 <br>
     */

    @Transactional
    @DeleteMapping("/delete")
    public Result<Object> delete(@RequestParam("ids") List<Integer> ids) {
        log.info("批量(单个)删除文章");
        articleService.deleteArticleByIds(ids);

        return Result.success();
    }

    /**
     * 显示文章信息
     */
    @GetMapping("/show")
    public Result<PageResult> show(@RequestBody PageSearchDTO pageSearchDTO) {
        log.info("显示(回收站的)文章信息");
        PageResult pageResult =  recycleBinService.show(pageSearchDTO);

        return Result.success(pageResult);
    }
}
