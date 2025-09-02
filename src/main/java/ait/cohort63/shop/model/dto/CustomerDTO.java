package ait.cohort63.shop.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Objects;

@Schema(description = "DTO for Customer")

public class CustomerDTO {

    @Schema(description = "customerDTO unique identifier", example = "8888", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "customer name", example = "Mihalich")
    private String name;


    public CustomerDTO() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Customer: id - %d, name - %s, active - %s");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        CustomerDTO that = (CustomerDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        return result;
    }
}
