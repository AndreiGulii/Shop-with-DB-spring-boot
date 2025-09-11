package ait.cohort63.shop.exeption_handling.exceptions;

public class DuplicateProductTitleException extends RuntimeException{
    public DuplicateProductTitleException(String title) {
        super("Product with title " + title + " already exists");
    }
}
