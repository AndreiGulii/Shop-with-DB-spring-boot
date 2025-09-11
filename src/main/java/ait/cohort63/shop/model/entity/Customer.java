package ait.cohort63.shop.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * @author Sergey Bugaenko
 * {@code @date} 11.09.2025
 */

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;

    @Column
    private boolean active;

    @OneToOne(mappedBy = "customer")
    private Cart cart;

    @Override
    public String toString() {
        return String.format("Customer: id - %d, name - %s, active - %s, Cart - %s",
                id, name, active ? "yes" : "no", cart == null ? "null" : cart );
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Customer customer)) return false;

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

    public Cart  getCart() {
        return cart;
    }

    public void setCart(Cart  cart) {
        this.cart = cart;
    }
}