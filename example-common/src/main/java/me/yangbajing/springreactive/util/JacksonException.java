package me.yangbajing.springreactive.util;

public class JacksonException extends RuntimeException {
    private int status;

    public JacksonException(String message, Throwable cause, boolean enableSuppression, boolean writeableStackTrace) {
        super(message, cause, enableSuppression, writeableStackTrace);
        this.status = 500;
    }

    public JacksonException(String message) {
        super(message);
        this.status = 500;
    }

    public JacksonException(Throwable cause) {
        super(cause);
        this.status = 500;
    }

    public JacksonException(String message, Throwable cause) {
        super(message, cause);
        this.status = 500;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
