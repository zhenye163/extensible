package com.netopstec.extensible.config;

import com.netopstec.extensible.task.QuartzScheduleTask;
import lombok.Getter;
import lombok.Setter;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * quartz定时任务的配置类
 * @author zhenye 2018/9/21
 */
@Configuration
@ConfigurationProperties("quartz")
@Getter
@Setter
public class QuartzScheduleConfig {

    private String schedulerName;
    private String threadCount;
    private String threadNamePrefix;
    private String tasks;
    private final ApplicationContext context;

    @Autowired
    public QuartzScheduleConfig(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        Properties properties = new Properties();
        properties.setProperty("org.quartz.threadPool.threadCount", threadCount);
        properties.setProperty("org.quartz.threadPool.threadNamePrefix", threadNamePrefix);
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setSchedulerName(schedulerName);
        factory.setQuartzProperties(properties);
        return factory;
    }

    @Bean
    public Scheduler scheduler() throws Exception {
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        scheduler.scheduleJobs(createJobDetails(), true);
        return scheduler;
    }

    /**
     * 创建JobDetail
     * 使用是Spring的{@link MethodInvokingJobDetailFactoryBean}来创建
     * 使用Spring的{@link CronTriggerFactoryBean}来创建CronTrigger
     *
     * @return Map<JobDetail, Set<CronTrigger>>
     * @throws Exception 异常
     */
    private Map<JobDetail, Set<? extends Trigger>> createJobDetails() throws Exception {
        Set<String> taskSet = StringUtils.commaDelimitedListToSet(tasks);
        Map<JobDetail, Set<? extends Trigger>> map = new HashMap<>(taskSet.size());
        for (String task : taskSet) {
            String[] nameAndCron = task.split(":");
            String name = StringUtils.uncapitalize(nameAndCron[0]);
            String cron = nameAndCron[1];
            MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
            factoryBean.setTargetObject(context.getBean(name));
            factoryBean.setName(name);
            factoryBean.setTargetMethod(QuartzScheduleTask.class.getMethods()[0].getName());
            factoryBean.afterPropertiesSet();
            CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
            cronTriggerFactoryBean.setCronExpression(cron);
            cronTriggerFactoryBean.setJobDetail(factoryBean.getObject());
            cronTriggerFactoryBean.setName(name);
            cronTriggerFactoryBean.afterPropertiesSet();
            Set<CronTrigger> cronTriggerSet = new HashSet<>(1);
            cronTriggerSet.add(cronTriggerFactoryBean.getObject());
            map.put(factoryBean.getObject(), cronTriggerSet);
        }
        return map;
    }
}
