package com.netopstec.extensible.task;

/**
 * 由于将数据进行了分库（master数据库：支持增、删、改操作；slave数据库：支持查操作）
 * 为了保证数据的最终一致性，需要制定一个定时任务：
 * 每过10分钟，要将master数据库最近15分钟更新过的数据，同步更新到slave数据库中
 * @author zhenye 2018/9/21
 */
public class SyncSlaveDataTask implements QuartzScheduleTask{

    @Override
    public void exec() {
        // TODO 同步slave的数据
    }
}
