package com.netopstec.extensible.annotation;

import com.netopstec.extensible.common.DataSourceTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义使用数据源的注解
 * @author zhenye 2018/9/17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSourceType {
    DataSourceTypeEnum value() default DataSourceTypeEnum.master;
}
