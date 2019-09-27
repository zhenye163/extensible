package com.netopstec.extensible.util;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 基于quartz的定时任务管理工具包
 * @author zhenye 2019/9/27
 */
@Slf4j
@Component
@EnableScheduling
public class ScheduleUtil {

    /**
     * 校验Cron表达式
     */
    public static boolean validateCronExpression(String cronExpression)  {
        boolean result = CronExpression.isValidExpression(cronExpression);
        if (!result) {
            log.error("The cronExpression【{}】 is not correct!", cronExpression);
        }
        return result;
    }

    /**
     * 创建任务
     */
    public static boolean createScheduleJob(Scheduler scheduler, Class<? extends Job> jobClass, String scheduleJobName, String cronExpression, String triggerName, String scheduleJobDescription, Boolean runImmediately)  {
        if (!validateCronExpression(cronExpression)) {
            return false;
        }
        try {
            // 要执行的 Job 的类
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(scheduleJobName)
                    .withDescription(scheduleJobDescription)
                    .build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
                    .withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName)
                    .withDescription(scheduleJobDescription)
                    .withSchedule(scheduleBuilder)
                    .startNow()
                    .build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            log.info("Create scheduleJob【{}】-【{}】 success.", jobClass.getName(), cronExpression);
            if (!runImmediately) {
                return !pauseJob(scheduler, scheduleJobName);
            }
            return true;
        } catch (Exception e) {
            log.error("Create scheduleJob fail -> ", e);
            return false;
        }
    }

    /**
     * 暂停任务
     */
    public static boolean pauseJob(Scheduler scheduler, String scheduleJobName)  {
        try {
            scheduler.pauseJob(getJobKey(scheduleJobName));
            log.info("Pause scheduleJob 【{}】 success.", scheduleJobName);
            return true;
        } catch (SchedulerException e) {
            log.error("Pause scheduleJob failed -> ", e);
            return false;
        }
    }

    /**
     * 获取 JobKey
     */
    private static JobKey getJobKey(String scheduleJobName) {
        return JobKey.jobKey(scheduleJobName);
    }

    /**
     * 更新任务
     */
    public static boolean updateScheduleJob(Scheduler scheduler, String scheduleJobName, String cronExpression, String triggerName, String scheduleJobDescription, Boolean runImmediately)  {
        if (!validateCronExpression(cronExpression)) {
            return false;
        }
        try {
            TriggerKey triggerKey = getTriggerKey(triggerName);
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
                    .withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = getCronTrigger(scheduler, triggerName);
            if (cronTrigger == null) {
                return false;
            }
            cronTrigger = cronTrigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withDescription(scheduleJobDescription)
                    .withSchedule(cronScheduleBuilder).build();
            scheduler.rescheduleJob(triggerKey, cronTrigger);
            log.info("Update scheduleJob 【{}】 success.", scheduleJobName);
            if (!runImmediately) {
                return !pauseJob(scheduler, scheduleJobName);
            }
            return true;
        } catch (SchedulerException e) {
            log.error("Update scheduleJob failed -> ", e);
            return false;
        }
    }

    /**
     * 获取 TriggerKey
     */
    private static TriggerKey getTriggerKey(String triggerName) {
        return TriggerKey.triggerKey(triggerName);
    }

    /**
     * 获取 CronTrigger
     */
    private static CronTrigger getCronTrigger(Scheduler scheduler, String triggerName) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(triggerName));
        } catch (SchedulerException e) {
            log.error("Get Cron trigger failed ", e);
            return null;
        }
    }

    /**
     * 执行任务
     */
    public static boolean run(Scheduler scheduler, String scheduleJobName) {
        try {
            scheduler.triggerJob(getJobKey(scheduleJobName));
            log.info("Run scheduleJob 【{}】 success.", scheduleJobName);
            return true;
        } catch (SchedulerException e) {
            log.error("Run scheduleJob failed -> ", e);
            return false;
        }
    }


    /**
     * 继续执行任务
     */
    public static boolean resumeJob(Scheduler scheduler, String scheduleJobName)  {
        try {
            scheduler.resumeJob(getJobKey(scheduleJobName));
            log.info("Resume scheduleJob 【{}】 success.", scheduleJobName);
            return true;
        } catch (SchedulerException e) {
            log.error("Resume scheduleJob failed -> ", e);
            return false;
        }
    }

    /**
     * 删除任务
     */
    public static boolean deleteJob(Scheduler scheduler, String scheduleJobName)  {
        try {
            scheduler.deleteJob(getJobKey(scheduleJobName));
            log.info("Delete scheduleJob 【{}】 success", scheduleJobName);
            return true;
        } catch (SchedulerException e) {
            log.error("Delete scheduleJob failed -> ", e);
            return false;
        }
    }
}
