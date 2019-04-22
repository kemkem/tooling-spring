package net.kprod.tooling.spring.starter.data.response;

/**
 * Default exception response data bean
 */
public class ResponseException {
    private String message;
    private String stacktrace;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }
}