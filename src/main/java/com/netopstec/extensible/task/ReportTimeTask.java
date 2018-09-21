package com.netopstec.extensible.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhenye 2018/9/21
 */
@Slf4j
@Component
public class ReportTimeTask implements QuartzScheduleTask{

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void exec() {
        log.info("执行的Quartz指定的任务，现在的时间是：{}",sdf.format(new Date()));
    }
}
