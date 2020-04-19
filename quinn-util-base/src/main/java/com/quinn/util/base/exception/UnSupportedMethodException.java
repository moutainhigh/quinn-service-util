package com.quinn.util.base.exception;

import com.quinn.util.constant.enums.ExceptionEnums;

/**
 * 不支持的方法
 *
 * @author Qunhua.Liao
 * @since 2020-03-28
 */
public class UnSupportedMethodException extends BaseBusinessException {

    {
        buildParam(ExceptionEnums.METHOD_NOT_SUPPORTED.name(), 2, 0);
    }

    public UnSupportedMethodException() {
        super();
    }

    public UnSupportedMethodException(String message) {
        super(message);
    }

    public UnSupportedMethodException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
