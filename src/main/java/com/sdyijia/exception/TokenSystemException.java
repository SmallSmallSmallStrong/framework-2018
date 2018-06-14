package com.sdyijia.exception;

/**
 * token系统异常
 * @author zhqy
 *
 */
public class TokenSystemException extends Exception {

    private static final long serialVersionUID = 1L;
    

    public TokenSystemException() {
        super();
    }

    public TokenSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TokenSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenSystemException(String message) {
        super(message);
    }

    public TokenSystemException(Throwable cause) {
        super(cause);
    }
    
}
