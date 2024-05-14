package com.zone.controller;

import com.zone.result.Result;
import com.zone.service.RecycleBinService;
import com.zone.vo.ArticleRecycleBinVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 回收站控制器
 *
 */
@RestController
@RequestMapping("/bin")
@Slf4j
@Api(tags = "回收站管理")
public class RecycleBinController {
    @Autowired
    private RecycleBinService recycleBinService;

    /**
     * 定时七天清理
     * 使用Spring-task进行定时任务
     */
    // TODO 由于时间过长 无法直接演示 所以更改时间为定时1分钟清理一次
    //@Scheduled(cron = "0 0 0 1/7 * ?") // 定时七天清理
    @Scheduled(cron = "0 1 * * * ?") // 定时一分钟清理
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

    @DeleteMapping("/delete")
    @ApiOperation("批量(单个)删除文章")
    @Transactional
    public Result delete(@RequestParam("ids") List<Integer> ids) {
        //TODO 由于删除文章 关联多个表 所以需要事务管理
        // 并且 删除文章时 需要删除文章的评论
        // 还要删除文章的分类 评论的回复
        // 所以暂时不做
        return Result.success();
    }

    /**
     * 显示文章信息
     */
    @PostMapping("/show")
    @ApiOperation("显示文章信息")
    public Result<List<ArticleRecycleBinVO>> show() {
        List<ArticleRecycleBinVO> show = recycleBinService.show();
        log.info("显示文章信息");
        return Result.success(show);
    }


}
