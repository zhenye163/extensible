package com.netopstec.extensible.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhenye 2018/9/11
 */
@AllArgsConstructor
@Getter
public enum  ErrorCodeEnum {
    /**
     * 自定义异常里面的异常信息
     */
    SYSTEM_ERROR("系统错误"),
    MISS_INFO_ERROR("信息缺失错误"),
    OPERATION_DENY_ERROR("被禁止的操作错误"),
    BY_ZERO_ERROR("除0错误");
    private String msg;
}
