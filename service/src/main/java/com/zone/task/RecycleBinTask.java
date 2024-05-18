//package com.zone.task;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.transaction.annotation.Transactional;
//
//
//public class RecycleBinTask {
//
//    /**
//     * 定时七天清理
//     * 使用Spring-task进行定时任务
//     */
//
////    @Scheduled(cron = "0 0 0 1/7 * ?") // 定时七天清理
////    @Transactional
////    public void clear() {
////        // 查询数据库中1分钟前的数据
////        // 进行删除
////        log.info("定时清理回收站");
////        recycleBinService.clear();
////    }
////}
