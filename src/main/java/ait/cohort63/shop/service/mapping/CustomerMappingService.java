package ait.cohort63.shop.service.mapping;

import ait.cohort63.shop.model.dto.CustomerDTO;
import ait.cohort63.shop.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CustomerMappingService {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Customer mapDTOToEntity(CustomerDTO customerDTO);

    CustomerDTO mapEntityToDTO(Customer customer);
}
