package ait.cohort63.shop.service.interfaces;

import ait.cohort63.shop.model.dto.CartDTO;
import ait.cohort63.shop.model.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {


    CartDTO createCartForCustomer(Long customerId);

    //Добавить продукт в корзину (только если продукт активный).
    CartDTO addProductToCart(Long cartId, Long productId);

    //Получить все активные продукты из корзины.
    List<ProductDTO> getAllActiveProductsFromCart(Long cartId);

    //Удалить продукт из корзины по его ID.
    CartDTO removeProductFromCart(Long cartId, Long productId);

    //Полностью очистить корзину (удалить все продукты)
    CartDTO clearCart(Long cartId);

    //Получение общей стоимости корзины (активных продуктов)
    BigDecimal getTotalPrice(Long cartId);

    //Получить среднюю стоимость активных товаров в корзине.
    BigDecimal getAveragePrice(Long cartId);
}