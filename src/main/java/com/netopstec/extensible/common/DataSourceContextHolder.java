package com.netopstec.extensible.common;

/**
 * 定义一个线程安全的、用来保存将要使用的数据源类型的Map容器
 * @author zhenye 2018/9/17
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<DataSourceTypeEnum> CONTEXT_HOLDER = ThreadLocal.withInitial(() -> DataSourceTypeEnum.master);

    public static void setDataSourceType(DataSourceTypeEnum dataSourceTypeEnum){
        CONTEXT_HOLDER.set(dataSourceTypeEnum);
    }

    public static DataSourceTypeEnum getDataSourceType(){
        return CONTEXT_HOLDER.get();
    }
}
