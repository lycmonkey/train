package com.lyc.batch.job;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.lyc.batch.feign.BusinessFeign;
import jakarta.annotation.Resource;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Date;

/**
 * @author lyc
 */
// 禁止并发执行任务
@DisallowConcurrentExecution
public class DailyTrainJob implements Job {

    @Resource
    private BusinessFeign businessFeign;

    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        MDC.put("LOG_ID", System.currentTimeMillis() + Thread.currentThread().getId() + "");
        LOG.info("生成每日车次数据开始");
        Date date = new Date();
        for (int i = 0; i < 16; i++) {
            final DateTime dateTime = DateUtil.offsetDay(date, i);
            final Date offsetDate = dateTime.toJdkDate();
            businessFeign.genDaily(offsetDate);
        }
        LOG.info("生成每日车次数据结束");

    }
}
