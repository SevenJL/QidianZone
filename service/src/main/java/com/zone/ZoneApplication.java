package com.zone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement // 启动事务管理
@EnableScheduling // 启动任务调度
@ServletComponentScan // 扫描Servlet组件
public class ZoneApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ZoneApplication.class, args);
    }
}
