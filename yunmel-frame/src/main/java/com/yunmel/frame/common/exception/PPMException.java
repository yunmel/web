package com.yunmel.frame.common.exception;

import org.springframework.util.StringUtils;

import com.yunmel.frame.common.utils.MessageUtils;

/**
 * 基础异常
 */
@SuppressWarnings("serial")
public class PPMException extends RuntimeException {

	//所属模块
    private String module;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;


    public PPMException(String module, String code, Object[] args, String defaultMessage) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public PPMException(String module, String code, Object[] args) {
        this(module, code, args, null);
    }

    public PPMException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public PPMException(String code, Object[] args) {
        this(null, code, args, null);
    }

    public PPMException(String defaultMessage) {
        this(null, null, null, defaultMessage);
    }

    @Override
    public String getMessage() {
        String message = null;
        if (!StringUtils.isEmpty(code)) {
            message = MessageUtils.message(code, args);
        }
        if (message == null) {
            message = defaultMessage;
        }
        return message;
    }


    public String getModule() {
        return module;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    @Override
    public String toString() {
        return this.getClass() + "{" +
                "module='" + module + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
