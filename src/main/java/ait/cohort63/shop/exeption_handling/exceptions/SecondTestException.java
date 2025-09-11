package ait.cohort63.shop.exeption_handling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SecondTestException extends RuntimeException {

    public SecondTestException(String message) {
        super(message);
    }
}
