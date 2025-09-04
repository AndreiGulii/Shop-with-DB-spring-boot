package ait.cohort63.shop.model.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;


@Schema(description = "DTO for cart")
public class CartDTO {

    @Schema(description = "cartDTO unique identifier", example = "555", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Owner of the cart(Customer ID)")
    private Long customerId;

    @Schema(description = "List of products in the cart")
    private List<ProductDTO>  products;

    public CartDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return String.format("Cart: id - %s, customerId - %s, products count - %d", id, customerId == null ?  "null" : customerId.toString(), products ==null ? 0 : products.size());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        CartDTO cartDTO = (CartDTO) o;
        return Objects.equals(id, cartDTO.id) && Objects.equals(customerId, cartDTO.customerId) && Objects.equals(products, cartDTO.products);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(customerId);
        result = 31 * result + Objects.hashCode(products);
        return result;
    }
}
