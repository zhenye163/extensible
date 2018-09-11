package com.netopstec.extensible.controller.advice;

import com.netopstec.extensible.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局的异常处理类
 * @author zhenye 2018/9/11
 */
@RestControllerAdvice
@Slf4j
public class RestControllerExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public void handleBaseException(BaseException baseException){
        log.error("There is a baseException to resolve. The error is --->",baseException);
    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception exception){
        log.error("There is a exception to resolve. The error is --->",exception);
    }
}
