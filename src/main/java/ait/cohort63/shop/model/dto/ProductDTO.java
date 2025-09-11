package ait.cohort63.shop.model.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

@Schema(description = "DTO for Product")
public class ProductDTO {

    @Schema(description = "productDTO unique identifier", example = "7777", accessMode =  Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Product title", example = "Banana")
    @NotNull(message = "Product title can not be null")
    @NotBlank(message = "Product title can not be empty")
    // razreshaiem pervuiu Bolishuiu bukvu a vtoruiu i posledujushie malenikiie lat. bukvi i min kol-vo bukv 2
    @Pattern(regexp = "^[A-Z][a-z ]{2,}$", message = "Product title should be at least 2 characters and starts wit a Capitasl letter, contains only Letters and spaces")
    private String title;

    @DecimalMin(value = "0.5", message = "product price should be greater or equal 0.5 €")
    @DecimalMax(value = "100000", inclusive = false, message = "price should be less then 100_000 €")
    @Positive(message = "Product price can be only positive")
    @Schema(description = "Product price", example = "8.50")
    private BigDecimal price;

    @Override
    public String toString() {
        return String.format("Product: id - %d, title - %s, price - %s",
                id, title, price);
    }


    public ProductDTO() {
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

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ProductDTO that = (ProductDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(price);
        return result;
    }


}
