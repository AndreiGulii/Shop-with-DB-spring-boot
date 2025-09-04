package ait.cohort63.shop.controller;

import ait.cohort63.shop.model.dto.CartDTO;
import ait.cohort63.shop.model.dto.ProductDTO;
import ait.cohort63.shop.service.interfaces.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/carts")
@CrossOrigin(origins = "*") // можно ограничить доменом фронта
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Создать корзину для конкретного покупателя
    @PostMapping("/{customerId}")
    public ResponseEntity<CartDTO> createCartForCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.createCartForCustomer(customerId));
    }

    // Добавить продукт в корзину
    @PostMapping("/{cartId}/products/{productId}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long cartId,
                                                    @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.addProductToCart(cartId, productId));
    }

    // Получить все активные продукты из корзины
    @GetMapping("/{cartId}/products")
    public ResponseEntity<List<ProductDTO>> getAllActiveProductsFromCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getAllActiveProductsFromCart(cartId));
    }

    // Удалить продукт из корзины
    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<CartDTO> removeProductFromCart(@PathVariable Long cartId,
                                                         @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeProductFromCart(cartId, productId));
    }

    // Очистить корзину
    @DeleteMapping("/{cartId}")
    public ResponseEntity<CartDTO> clearCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.clearCart(cartId));
    }

    // Общая стоимость корзины
    @GetMapping("/{cartId}/total-price")
    public ResponseEntity<BigDecimal> getTotalPrice(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getTotalPrice(cartId));
    }

    // Средняя стоимость продуктов в корзине
    @GetMapping("/{cartId}/average-price")
    public ResponseEntity<BigDecimal> getAveragePrice(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getAveragePrice(cartId));
    }
}