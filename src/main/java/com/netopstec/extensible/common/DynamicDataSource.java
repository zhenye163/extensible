package com.netopstec.extensible.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源的配置。
 * 必须要继承AbstractRoutingDataSource，且只需实现determineCurrentLookupKey()方法，指定当前使用的数据源就行。
 * @author zhenye 2018/9/17
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        log.info("当前接口使用的数据源为{}", DataSourceContextHolder.getDataSourceType());
        return DataSourceContextHolder.getDataSourceType();
    }
}
