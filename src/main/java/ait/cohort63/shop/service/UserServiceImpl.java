package ait.cohort63.shop.service;

import ait.cohort63.shop.model.dto.UserRegisterDTO;
import ait.cohort63.shop.model.entity.User;
import ait.cohort63.shop.repository.UserRepository;
import ait.cohort63.shop.service.interfaces.EmailService;
import ait.cohort63.shop.service.interfaces.RoleService;
import ait.cohort63.shop.service.interfaces.UserService;
import ait.cohort63.shop.service.mapping.UserMapperService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;
    private final EmailService  emailService;
    private final UserMapperService mapper;


    public UserServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder encoder, EmailService emailService, UserMapperService mapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.encoder = encoder;
        this.emailService = emailService;
        this.mapper = mapper;
    }

    @Override
    @Transactional//delaiet vipolneniie vseh metodov v ramkah odnoi transaktsii
    public void registerUser(UserRegisterDTO userRegisterDTO) {

        User user = mapper.mapDTOToEntity(userRegisterDTO);

        Optional<User> optionalUser = userRepository.findUserByEmail(user.getEmail());

        //Proverka sushestvovaniia polizovatelea s takim emailom
        if (userRepository.existsByEmail(user.getEmail()) && optionalUser.get().isActive()){
            throw new RuntimeException("Email is already in use");
        }

        if (optionalUser.isPresent()){
            user = optionalUser.get();
        }else{
            user.setRoles(Set.of(roleService.getRoleUser()));
        }

        user.setPassword(encoder.encode(userRegisterDTO.password()));

        user.setActive(false);

        userRepository.save(user);

        // posle uspeshnogo sohranenija v baze nujno otpraviti pisimo
        emailService.sendConfirmationEmail(user);
    }
}
