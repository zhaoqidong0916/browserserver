package com.ht.common.exceptions.exception;

/**
 * ParamInsertException
 * @author lin.cong
 *
 */
public class BrowserException extends RuntimeException {
    public BrowserException() {
    }

    public BrowserException(String message) {
        super(message);
    }

    public BrowserException(String message, Throwable cause) {
        super(message, cause);
    }

    public BrowserException(Throwable cause) {
        super(cause);
    }
}
