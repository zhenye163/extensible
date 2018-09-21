package com.netopstec.extensible.task;

/**
 * 需要制定一个任务，来同步redis与master中的数据(明天凌晨1:30)
 * @author zhenye 2018/9/21
 */
public class SyncRedisDataTask implements QuartzScheduleTask {
    @Override
    public void exec() {
        // TODO 同步redis中的数据
    }
}
