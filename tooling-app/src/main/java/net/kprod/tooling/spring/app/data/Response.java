package net.kprod.tooling.spring.app.data;

/**
 * A simple default data bean
 */
public class Response {
    private String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
