package ait.cohort63.shop.service;


import ait.cohort63.shop.model.entity.Product;
import ait.cohort63.shop.service.interfaces.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public Product saveProduct(Product product) {
        return null;
    }

    @Override
    public List<Product> getAllActiveProducts() {
        return List.of();
    }

    @Override
    public Product getProductById(Long id) {
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product deleteProductById(Long id) {
        return null;
    }

    @Override
    public Product deleteProductByTitle(String title) {
        return null;
    }

    @Override
    public Product restoreProductById(Long id) {
        return null;
    }

    @Override
    public long getProductCount() {
        return 0;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getAveragePrice() {
        return null;
    }
}
