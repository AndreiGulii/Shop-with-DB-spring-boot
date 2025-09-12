package ait.cohort63.shop.service.mapping;

import ait.cohort63.shop.model.dto.UserRegisterDTO;
import ait.cohort63.shop.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapperService {

    @Mapping(target = "id", ignore = true)
    User mapDTOToEntity(UserRegisterDTO dto);
}
