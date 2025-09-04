package ait.cohort63.shop.model.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name="customer")
public class Customer {
    @Schema(description = "customer unique identifier", example = "8888", accessMode =  Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Schema(description = "Customer name", example = "Petrovich")
    @Column
    private String name;

    @Schema(description = "Customer active or not", example = "active", accessMode =  Schema.AccessMode.READ_ONLY )
    @Column
    private boolean active;

    public Customer() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return String.format("Customer: id - %d, name - %s, active - %s" ,  id, name, active ? "Yes" : "No");
    }
    @Override
    public final boolean equals(Object o) {
        if (!(o  instanceof Customer customer)) return false;
        return active == customer.active && Objects.equals(id, customer.id) && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Boolean.hashCode(active);
        return result;
    }

    public void setCart(Cart cart) {

    }
}
