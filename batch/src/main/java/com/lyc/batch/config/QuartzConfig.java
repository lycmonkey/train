//package com.lyc.batch.config;
//
//import com.lyc.batch.job.TestJob;
//import org.quartz.*;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author lyc
// */
//@Configuration
//public class QuartzConfig {
//
//    /**
//     * 声明一个任务
//     * @return
//     */
//    @Bean
//    public JobDetail jobDetail() {
//        return JobBuilder.newJob(TestJob.class)
//                .withIdentity("TestJob", "test")
//                .storeDurably().build();
//    }
//
//    /**
//     * 声明一个触发器，什么时候触发这个任务
//     * @return
//     */
//    @Bean
//    public Trigger trigger() {
//        return TriggerBuilder.newTrigger()
//                .forJob(jobDetail())
//                .withIdentity("Trigger", "trigger")
//                .startNow()
//                .withSchedule(CronScheduleBuilder.cronSchedule("*/2 * * * * ?"))
//                .build();
//    }
//
//}
