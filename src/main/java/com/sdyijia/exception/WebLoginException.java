package com.sdyijia.exception;

/**
 * 前台登录异常
 * @author zhqy
 *
 */
public class WebLoginException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    

    public WebLoginException() {
        super();
    }

    public WebLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public WebLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebLoginException(String message) {
        super(message);
    }

    public WebLoginException(Throwable cause) {
        super(cause);
    }
    
}
