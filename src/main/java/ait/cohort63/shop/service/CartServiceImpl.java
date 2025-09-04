package ait.cohort63.shop.service;

import ait.cohort63.shop.model.dto.CartDTO;
import ait.cohort63.shop.model.dto.ProductDTO;
import ait.cohort63.shop.model.entity.Cart;
import ait.cohort63.shop.model.entity.Customer;
import ait.cohort63.shop.model.entity.Product;
import ait.cohort63.shop.repository.CartRepository;
import ait.cohort63.shop.repository.CustomerRepository;
import ait.cohort63.shop.repository.ProductRepository;
import ait.cohort63.shop.service.interfaces.CartService;
import ait.cohort63.shop.service.mapping.CartMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMappingService mapper;
    private final CustomerRepository customerRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           ProductRepository productRepository,
                           CustomerRepository customerRepository,
                           CartMappingService mapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public CartDTO createCartForCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            return null; // можно выбросить исключение или вернуть null -> контроллер обработает
        }
        Cart cart = new Cart();
        cart.setCustomer(customer);
        if (cart.getProducts() == null) cart.setProducts(new ArrayList<>());
        cart = cartRepository.save(cart);
        return mapper.mapEntityToDTO(cart);
    }

    @Override
    public CartDTO addProductToCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) return null;

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return mapper.mapEntityToDTO(cart);

        if (!product.isActive()) return mapper.mapEntityToDTO(cart);

        if (cart.getProducts() == null) cart.setProducts(new ArrayList<>());
        cart.getProducts().add(product);
        cart = cartRepository.save(cart);
        return mapper.mapEntityToDTO(cart);
    }

    @Override
    public List<ProductDTO> getAllActiveProductsFromCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null || cart.getProducts() == null) return List.of();
        return cart.getProducts().stream()
                .filter(Product::isActive)
                .map(mapper::mapProductToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CartDTO removeProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null || cart.getProducts() == null) return null;
        cart.getProducts().removeIf(p -> Objects.equals(p.getId(), productId));
        cart = cartRepository.save(cart);
        return mapper.mapEntityToDTO(cart);
    }


    @Override
    public CartDTO clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) return null;
        if (cart.getProducts() != null) cart.getProducts().clear();
        cart = cartRepository.save(cart);
        return mapper.mapEntityToDTO(cart);
    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null || cart.getProducts() == null) return BigDecimal.ZERO;
        return cart.getProducts().stream()
                .filter(Product::isActive)
                .map(Product::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getAveragePrice(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null || cart.getProducts() == null) return BigDecimal.ZERO;
        List<BigDecimal> prices = cart.getProducts().stream()
                .filter(Product::isActive)
                .map(Product::getPrice)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (prices.isEmpty()) return BigDecimal.ZERO;
        BigDecimal total = prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return total.divide(new BigDecimal(prices.size()), 2, RoundingMode.HALF_UP);
    }
}
