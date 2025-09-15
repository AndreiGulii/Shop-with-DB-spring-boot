package ait.cohort63.shop.exeption_handling;

import java.util.Objects;

public class Response {
    private String message;
    public String url;
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
        return Objects.equals(message, response.message) && Objects.equals(url, response.url);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(message);
        result = 31 * result + Objects.hashCode(url);
        return result;
    }

    @Override
    public String toString() {
        return "Response: message - " + message;
    }

    public Response(String message, String url) {
        this.message = message;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
