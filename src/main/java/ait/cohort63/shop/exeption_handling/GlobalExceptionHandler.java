package ait.cohort63.shop.exeption_handling;

import ait.cohort63.shop.exeption_handling.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ThirdTestException.class)
    public ResponseEntity<Response> handleThirdException(ThirdTestException ex) {
        Response response = new Response(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }
    //@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationException(MethodArgumentNotValidException ex) {
        //Polea s oshibkami hraneatsa v obiekte BindingResult
        //FieldError - klass. Oshibka sveazannaia s konkretnim polem

        // Sozdaiom String bilder dlea nakoplenija soobshenii ob oshibkah
        StringBuilder errorMessage = new StringBuilder();

        // perebiraiem vse oshibki validatsii
        for(FieldError error : ex.getBindingResult().getFieldErrors()) {
            // Dobavleiaiem soobshenije ob Oshibke dlea tekushego polea
            errorMessage.append(error.getDefaultMessage()).append("; ");

        }
        //Sozdaiom objekt response  s nakoplennih soobshenijah ob oshibkah
        Response response = new Response(errorMessage.toString());
        //vozvrasheaiem ResponseEntity s obiektom Response i statusom Bad_Request
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationResponse> handleValidationExceptionV2(MethodArgumentNotValidException ex) {
        //Polea s oshibkami hraneatsa v obiekte BindingResult
        //FieldError - klass. Oshibka sveazannaia s konkretnim polem

        // Sozdaiom String bilder dlea nakoplenija soobshenii ob oshibkah
        List<String>errors = new ArrayList<>();

        // perebiraiem vse oshibki validatsii
        for(FieldError error : ex.getBindingResult().getFieldErrors()) {
            // Dobavleiaiem soobshenije ob Oshibke dlea tekushego polea
            errors.add(error.getField() + "(" + error.getRejectedValue() + ")" +  " -> " +  error.getDefaultMessage());

        }
        //Sozdaiom objekt response  s nakoplennih soobshenijah ob oshibkah
        ValidationResponse response = new ValidationResponse(errors);
        //vozvrasheaiem ResponseEntity s obiektom Response i statusom Bad_Request
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Response> handleNotFound(ProductNotFoundException ex) {
        return new ResponseEntity<>(new Response(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateProductTitleException.class)
    public ResponseEntity<Response> handleDuplicate(DuplicateProductTitleException ex) {
        return new ResponseEntity<>(new Response(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductAlreadyDeletedException.class)
    public ResponseEntity<Response> handleAlreadyDeleted(ProductAlreadyDeletedException ex) {
        return new ResponseEntity<>(new Response(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProductAlreadyRestoredException.class)
    public ResponseEntity<Response> handleAlreadyRestored(ProductAlreadyRestoredException ex) {
        return new ResponseEntity<>(new Response(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
