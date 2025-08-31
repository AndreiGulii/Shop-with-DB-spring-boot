package ait.cohort63.shop.controller;


import ait.cohort63.shop.model.entity.Product;
import ait.cohort63.shop.service.interfaces.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products") // Указывает, что контроллер обрабатывает запросы, связанные с ресурсом products
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {

        this.productService = productService;
    }


    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        // Todo обращаемся к сервису для сохранения продукта
        return productService.saveProduct(product);


    }

    // GET /products/1
    // GET /products/176
    // GET /products/55
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        // Todo обращаемся к сервису и запрашиваем продукт по id
        return productService.getProductById(id);
    }

    // GET /products
    @GetMapping
    public List<Product> getAll() {
        // TODO обращаемся к сервису и запрашиваем все продукты

        return productService.getAllActiveProducts();
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        // Todo обращаемся к серверу для обновления продукта
        return productService.updateProduct(id, product);
    }

    // DELETE -> /products/12
    @DeleteMapping("/{productId}")
    public Product remove(@PathVariable("productId") Long id) {
        // TODO - обращаемся к сервису для удаления
        return productService.deleteProductById(id);
    }

}

// POST /products - POST - определяет действие (создание нового), /products - определяет ресурс с которым совершает действие
// GET /products/2

// PUT /products/4
// DELETE /products/1

// /order/12/cancel
