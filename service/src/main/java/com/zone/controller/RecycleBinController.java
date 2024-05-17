package com.zone.controller;

import com.zone.dto.PageSearchDTO;
import com.zone.result.PageResult;
import com.zone.result.Result;
import com.zone.service.ArticleService;
import com.zone.service.RecycleBinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 回收站控制器
 *
 */
@Slf4j
@RestController
@Api(tags = "回收站管理")
@RequestMapping("/bin")
@AllArgsConstructor
public class RecycleBinController {
    private final RecycleBinService recycleBinService;

    private final ArticleService articleService;

    /**
     * 定时七天清理
     * 使用Spring-task进行定时任务
     */
    // TODO 由于时间过长 无法直接演示 所以更改时间为定时1分钟清理一次
    //@Scheduled(cron = "0 0 0 1/7 * ?") // 定时七天清理
    @Transactional
    @Scheduled(cron = "0 * * * * ?") // 定时一分钟清理
    public void clear() {
        // 查询数据库中1分钟前的数据
        // 进行删除
        log.info("定时清理回收站");
        recycleBinService.clear();
    }


    /**
     *  单个删除文章 和 批量删除文章
     *  删除单个 也是 删除批量 的一种
     *  所以直接写 删除批量文章 的方法
     */

    @Transactional
    @DeleteMapping("/delete")
    @ApiOperation("批量(单个)删除文章")
    public Result<Object> delete(@RequestParam("ids") List<Integer> ids) {
        log.info("批量(单个)删除文章");
        articleService.deleteArticleByIds(ids);

        return Result.success();
    }

    /**
     * 显示文章信息
     */
    @PostMapping("/show")
    @ApiOperation("显示(回收站的)文章信息")
    public Result<PageResult> show(@RequestBody PageSearchDTO pageSearchDTO) {
        log.info("显示(回收站的)文章信息");
        PageResult pageResult =  recycleBinService.show(pageSearchDTO);

        return Result.success(pageResult);
    }
}
