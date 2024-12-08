package com.lyc.batch.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author lyc
 * 自带的定时任务只适合单体不适合集群
 * 不能实时更改定时任务和策略
 */
// 开启定时任务
//@EnableScheduling
@Component
public class SpringBootTestJob {

    @Scheduled(cron = "0/5 * * * * ?")
    public void test() {
        System.out.println("SpringBootTestJob Test");
    }

}
