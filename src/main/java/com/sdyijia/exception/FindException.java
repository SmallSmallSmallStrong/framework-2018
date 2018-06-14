package com.sdyijia.exception;

/**
 * 查询异常 
 * @author zhqy
 *
 */
public class FindException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    

    public FindException() {
        super();
    }

    public FindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FindException(String message, Throwable cause) {
        super(message, cause);
    }

    public FindException(String message) {
        super(message);
    }

    public FindException(Throwable cause) {
        super(cause);
    }
    
}
