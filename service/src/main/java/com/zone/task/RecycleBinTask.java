package com.zone.task;

import com.zone.service.RecycleBinService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring-task 定时任务
 */

@AllArgsConstructor
@RestController
@Slf4j
public class RecycleBinTask {

    private final RecycleBinService recycleBinService;

    /**
     * 定时七天清理
     * 使用Spring-task进行定时任务
     */

    @Scheduled(cron = "1/60 * * * * ?") // 定时七天清理
    @Transactional
    public void clear() {
        // 查询数据库中1分钟前的数据
        // 进行删除
        log.info("定时清理回收站");
        recycleBinService.clear();
    }
}

