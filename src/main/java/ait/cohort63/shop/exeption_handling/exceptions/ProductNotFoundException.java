package ait.cohort63.shop.exeption_handling.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product with id " + id + " not found or inactive");
    }
}
