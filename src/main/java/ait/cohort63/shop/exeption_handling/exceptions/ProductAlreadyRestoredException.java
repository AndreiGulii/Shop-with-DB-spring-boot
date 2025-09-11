package ait.cohort63.shop.exeption_handling.exceptions;

public class ProductAlreadyRestoredException extends RuntimeException{
    public ProductAlreadyRestoredException(Long id) {
        super("Product with id: " + id + " is already restored");
    }
}
