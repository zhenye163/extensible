package com.netopstec.extensible.exception;

import com.netopstec.extensible.constant.ErrorCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhenye 2018/9/11
 */
@Getter
@Setter
@ToString
public class BaseException extends RuntimeException {

    private String code;
    private String message;

    public BaseException(ErrorCodeEnum errorCodeEnum,String message,Throwable throwable){
        super(message,throwable);
        this.code = errorCodeEnum.name();
        this.message = errorCodeEnum.getMsg();
    }

    public BaseException(ErrorCodeEnum errorCodeEnum,Exception e){
        this(errorCodeEnum,e.getMessage(),e);
    }

    public BaseException(ErrorCodeEnum errorCodeEnum){
        this(errorCodeEnum,null,null);
    }
}
