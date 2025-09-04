package ait.cohort63.shop.service.mapping;


import ait.cohort63.shop.model.dto.CartDTO;
import ait.cohort63.shop.model.dto.ProductDTO;
import ait.cohort63.shop.model.entity.Cart;
import ait.cohort63.shop.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMappingService {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    Cart mapDTOToEntity(CartDTO cartDTO);
    @Mapping(source = "customer.id", target = "customerId")
    CartDTO mapEntityToDTO(Cart cart);

    ProductDTO mapProductToDto(Product product);
}
