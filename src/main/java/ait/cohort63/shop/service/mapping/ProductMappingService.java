package ait.cohort63.shop.service.mapping;

import ait.cohort63.shop.model.dto.ProductDTO;
import ait.cohort63.shop.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMappingService {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Product mapDTOToEntity(ProductDTO productDTO);

    ProductDTO mapEntityToDTO(Product product);

//    public Product mapDTOToEntity(ProductDTO productDTO) {
//        Product product = new Product();
//        product.setTitle(productDTO.getTitle());
//        product.setPrice(productDTO.getPrice());
//        return product;
//    }
//
//    public ProductDTO mapEntityToDTO(Product product) {
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setId(product.getId());
//        productDTO.setTitle(product.getTitle());
//        productDTO.setPrice(product.getPrice());
//        return productDTO;
//    }
}
