package ait.cohort63.shop.service;

import ait.cohort63.shop.exeption_handling.exceptions.*;
import ait.cohort63.shop.model.dto.ProductDTO;
import ait.cohort63.shop.model.entity.Product;
import ait.cohort63.shop.repository.ProductRepository;
import ait.cohort63.shop.service.interfaces.ProductService;
import ait.cohort63.shop.service.mapping.ProductMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMappingService mapper;
    // SLF4J Biblioteka dlea logirovanija
   // private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository, ProductMappingService mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public ProductDTO saveProduct(ProductDTO  productDTO ) {
        boolean exists = productRepository
                .findAll()
                .stream()
                .anyMatch(p ->p
                        .getTitle()
                        .equalsIgnoreCase(productDTO
                                .getTitle()));
        if (exists) {
            throw new DuplicateProductTitleException(productDTO.getTitle());
        }
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
  //      logger.info("Method getProductById called with parameter: {}", id);
  //      logger.warn("Method getProductById called with parameter: {}", id);
  //      logger.error("Method getProductById called with parameter: {}", id);

        Product product = productRepository.findById(id).orElse(null);


        if (product == null || !product.isActive()){
 //           return null;
            throw new ProductNotFoundException(id);
        }
        return mapper.mapProductToDTO(product) ;
    }

    @Override
    public ProductDTO  updateProduct(Long id, ProductDTO  productDTO ) {

        return null;
    }

    @Override
    public ProductDTO  deleteProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        if(!product.isActive()){
            throw new ProductAlreadyDeletedException(id);
        }
        product.setActive(false);

        return mapper.mapProductToDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO  deleteProductByTitle(String title) {
    Product product = productRepository
            .findByTitleIgnoreCase(title)
            .orElseThrow(()-> new ProductNotFoundByTitleException(title));
    if (!product.isActive()) {
        throw new ProductAlreadyDeletedException(product.getId());
    }
    product.setActive(false);
        return mapper.mapProductToDTO(productRepository.save(product));
    }


    @Override
    public ProductDTO  restoreProductById(Long id) {
    Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    if (product.isActive()) {
        throw new ProductAlreadyRestoredException(id);
    }
    product.setActive(true);
        return  mapper.mapProductToDTO(productRepository.save(product));
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

    @Transactional// izmenenija primeniatsa k Base dannih
    @Override
    public void attachImage(String imageUrl, String productTitle) {
        // ishem produkt po nazvaniiu
        Product product= productRepository.findByTitleIgnoreCase(productTitle)
                .orElseThrow(() -> new ProductNotFoundByTitleException(productTitle));
        //Prisvaivaiem produktu ssilku
        product.setImage(imageUrl);
    }
}
