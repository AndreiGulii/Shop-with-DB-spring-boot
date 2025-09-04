package ait.cohort63.shop.controller;
import ait.cohort63.shop.model.dto.ProductDTO;
import ait.cohort63.shop.service.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;


// polucheniie vseh produktov- dostupno vsem polizovateleam, vkliuchaia anonimnih
// polucheniie produkta po id - toliko authentifizirovannie polizovateli
// sohranenije produkta v BD - toliko admini

@RestController
@RequestMapping("/products") // Указывает, что контроллер обрабатывает запросы, связанные с ресурсом products
@Tag(name = "Product controller", description = "Controller for operations with products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {

        this.productService = productService;
    }

    @Operation(summary = "Create product", description = "Add new product", tags = {"product"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDTO.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ProductDTO.class))})})


    @PostMapping
    public ProductDTO saveProduct(@Parameter(description = "Created product object") @RequestBody ProductDTO productDTO) {
        // обращаемся к сервису для сохранения продукта
        return productService.saveProduct(productDTO);


    }

    // GET /products/1
    // GET /products/176
    // GET /products/55

    @Operation(summary = "Get product by id", tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content =
                    {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class)),
                            @Content(mediaType = "application/xml",
                                    schema = @Schema(implementation = ProductDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)})

    @GetMapping("/{id}")
    public ProductDTO  getById(@Parameter(description = "The id needs to be fetched", required = true) @PathVariable Long id) {
        // обращаемся к сервису и запрашиваем продукт по id
        return productService.getProductById(id);
    }

    // GET /products
    @GetMapping
    public List<ProductDTO> getAll() {
        // обращаемся к сервису и запрашиваем все продукты
        return productService.getAllActiveProducts();
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        // обращаемся к серверу для обновления продукта
        return productService.updateProduct(id, productDTO);
    }

    // DELETE -> /products/12
    @DeleteMapping("/{productId}")
    public ProductDTO remove(@PathVariable("productId") Long id) {
        // обращаемся к сервису для удаления
        return productService.deleteProductById(id);
    }

    @DeleteMapping("/by-title")
    public ProductDTO removeByTitle(@RequestParam String title) {
        return productService.deleteProductByTitle(title);
    }

    @PutMapping("/restore/{id}")
    public ProductDTO restoreById(@PathVariable Long id) {
        return productService.restoreProductById(id);
    }

    @GetMapping("/count")
    public long getProductCount() {
        return productService.getProductCount();
    }

    @GetMapping("/total-price")
    public BigDecimal getTotalPrice() {
        return productService.getTotalPrice();
    }

    @GetMapping("/average-price")
    public BigDecimal getAveragePrice() {
        return productService.getAveragePrice();
    }

}

// POST /products - POST - определяет действие (создание нового), /products - определяет ресурс с которым совершает действие
// GET /products/2

// PUT /products/4
// DELETE /products/1

// /order/12/cancel
