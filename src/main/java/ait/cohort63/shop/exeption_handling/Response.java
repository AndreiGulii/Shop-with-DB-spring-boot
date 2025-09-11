package ait.cohort63.shop.exeption_handling;

import java.util.Objects;

public class Response {
    private String message;
    public Response(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
    this.message = message;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;
        return Objects.equals(message, response.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }
    @Override
    public String toString() {
        return "Response: message - " + message;
    }
}
