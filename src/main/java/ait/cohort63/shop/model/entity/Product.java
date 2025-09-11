package ait.cohort63.shop.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="product")
@Schema(description = "Class that described Product")
public class Product {

    @Schema(description = "product unique identifier", example = "7777", accessMode =  Schema.AccessMode.READ_ONLY)
    @Id // govorim baze chto primary key eto u nas id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//govorim chto id generiruietsa bazoi dannih
    @Column(name = "id")
    private Long id;

    @Schema(description = "Product title", example = "Banana")
    @Column(name = "title")
    private String title;

    @Schema(description = "Product price", example = "8.50")
    @Column(name = "price")
    private BigDecimal price;

    @Schema(description = "Product active or not", example = "active", accessMode =  Schema.AccessMode.READ_ONLY)
    @Column //Esli imea polea sovpadaiet s nazvaniem kolonki v BD to mojno ne pisati name="active"
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
