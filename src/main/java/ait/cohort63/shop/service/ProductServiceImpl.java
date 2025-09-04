package ait.cohort63.shop.service;


import ait.cohort63.shop.model.dto.ProductDTO;
import ait.cohort63.shop.model.entity.Product;
import ait.cohort63.shop.repository.ProductRepository;
import ait.cohort63.shop.service.interfaces.ProductService;
import ait.cohort63.shop.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMappingService mapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMappingService mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public ProductDTO saveProduct(ProductDTO  productDTO ) {
        Product product = mapper.mapDTOToProduct(productDTO);
        product.setActive(true);
        return mapper.mapProductToDTO(productRepository.save(product));
    }

    @Override
    public List<ProductDTO> getAllActiveProducts() {

        return productRepository.findAll()
                .stream()
                .filter(Product::isActive)
                .map(mapper::mapProductToDTO)
                .toList();
    }

    @Override
    public ProductDTO  getProductById(Long id) {

        Product product = productRepository.findById(id).orElse(null);
        if (product == null || !product.isActive()){
            return null;
        }
        return mapper.mapProductToDTO(product) ;
    }

    @Override
    public ProductDTO  updateProduct(Long id, ProductDTO  productDTO ) {

        return null;
    }

    @Override
    public ProductDTO  deleteProductById(Long id) {

        return null;
    }

    @Override
    public ProductDTO  deleteProductByTitle(String title) {

        return null;
    }

    @Override
    public ProductDTO  restoreProductById(Long id) {

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
