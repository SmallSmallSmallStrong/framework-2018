package com.sdyijia.exception;

/**
 * 保存更新异常
 * @author zhqy
 *
 */
public class SaveException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    

    public SaveException() {
        super();
    }

    public SaveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveException(String message) {
        super(message);
    }

    public SaveException(Throwable cause) {
        super(cause);
    }
    
}
