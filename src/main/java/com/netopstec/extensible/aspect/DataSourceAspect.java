package com.netopstec.extensible.aspect;

import com.netopstec.extensible.annotation.DataSourceType;
import com.netopstec.extensible.common.DataSourceContextHolder;
import com.netopstec.extensible.common.DataSourceTypeEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 数据源切面的配置
 * @author zhenye 2018/9/17
 */
@Aspect
@Component
public class DataSourceAspect {
    /**
      * 切点（匹配规则为：maper包下的所有方法）
      */
    @Pointcut("execution(* com.netopstec.extensible.mapper..*(..))")
    public void dataSourcePointcut(){}

    /**
     * 前置通知：
     * 逻辑是，扫描mapper包下的接口，没有@DataSourceType的注解，使用的数据源是master,
     * 注有@DataSourceType的注解，使用的数据源是该注解中value的值对应的数据源。
     */
    @Before("dataSourcePointcut()")
    public void before(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        DataSourceTypeEnum targetDataSourceType = DataSourceTypeEnum.master;
        if (method.isAnnotationPresent(DataSourceType.class)){
            DataSourceType setDataSourceType = method.getAnnotation(DataSourceType.class);
            targetDataSourceType = setDataSourceType.value();
        }
        DataSourceContextHolder.setDataSourceType(targetDataSourceType);
    }
}
