package ait.cohort63.shop.exeption_handling.exceptions;

public class ProductNotFoundByTitleException extends RuntimeException {
    public ProductNotFoundByTitleException(String title) {
        super("Product with title " + title + " not found");
    }
}
