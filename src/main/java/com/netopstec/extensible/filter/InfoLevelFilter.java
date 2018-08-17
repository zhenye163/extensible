package com.netopstec.extensible.filter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * 自定义过滤器，info.***.log中只打印INFO级别的日志
 * （如果不在配置文件中指定该过滤器，info.***.log或打印INFO以上所有级别的日志---root指定是INFO）
 * @author zhenye 2018/8/16
 */
public class InfoLevelFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        if (iLoggingEvent.getLevel().toInt() == Level.INFO.toInt()){
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
    }
}