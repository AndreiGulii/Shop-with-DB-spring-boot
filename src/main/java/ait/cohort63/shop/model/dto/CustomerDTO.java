package ait.cohort63.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Objects;

public class CustomerDTO {

    private Long id;
    private String name;
    private boolean active;
    @JsonManagedReference
    private CartDTO cart;

    @Override
    public String toString() {
        return String.format("Customer: id - %d, name - %s, active - %s, Cart - %s",
                id, name, active ? "yes" : "no", cart == null ? "null" : cart );
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof CustomerDTO customer)) return false;

        return active == customer.active && Objects.equals(id, customer.id) && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Boolean.hashCode(active);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }
}