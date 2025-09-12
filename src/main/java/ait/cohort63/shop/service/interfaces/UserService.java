package ait.cohort63.shop.service.interfaces;

import ait.cohort63.shop.model.dto.UserRegisterDTO;
import org.springframework.stereotype.Service;


public interface UserService {
    void registerUser(UserRegisterDTO userRegisterDTO);
}
