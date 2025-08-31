package ait.cohort63.shop.model.entity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Sergey Bugaenko
 * {@code @date} 29.08.2025
 */

public class Product {

    private Long id;
    private String title;
    private BigDecimal price;
    private boolean active;

    @Override
    public String toString() {
        return String.format("Product: id - %d, title - %s, price - %s, active - %s",
                id, title, price, active ? "yes" : "no");
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Product product)) return false;

        return active == product.active && Objects.equals(id, product.id) && Objects.equals(title, product.title) && Objects.equals(price, product.price);
    }

    public Product() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(price);
        result = 31 * result + Boolean.hashCode(active);
        return result;
    }
}
