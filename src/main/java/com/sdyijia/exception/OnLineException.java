package com.sdyijia.exception;

/**
 * 用户在线状态异常或用户不在线，继承MsgException
 * @author zhqy
 *
 */
public class OnLineException extends MsgException {

    private static final long serialVersionUID = 1L;
    

    public OnLineException() {
        super();
    }

    public OnLineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OnLineException(String message, Throwable cause) {
        super(message, cause);
    }

    public OnLineException(String message) {
        super(message);
    }

    public OnLineException(Throwable cause) {
        super(cause);
    }
    
}
