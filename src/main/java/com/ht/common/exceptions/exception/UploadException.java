package com.ht.common.exceptions.exception;

/**
 * ParamInsertException
 * @author lin.cong
 *
 */
public class UploadException extends RuntimeException {
    public UploadException() {
    }

    public UploadException(String message) {
        super(message);
    }

    public UploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public UploadException(Throwable cause) {
        super(cause);
    }
}
