package ait.cohort63.shop.exeption_handling.exceptions;

public class ProductAlreadyDeletedException extends RuntimeException {
    public ProductAlreadyDeletedException(Long id) {
        super("Product with id " + id + " is already deleted");
    }
}
