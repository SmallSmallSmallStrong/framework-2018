package com.sdyijia.exception;

import java.io.IOException;

public class DownloadException extends IOException {
    private static final long serialVersionUID = 1L;
    
    public DownloadException() {
        super();
    }

    public DownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public DownloadException(String message) {
        super(message);
    }

    public DownloadException(Throwable cause) {
        super(cause);
    }
    
}
