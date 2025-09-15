package ait.cohort63.shop.controller;

import ait.cohort63.shop.exeption_handling.Response;
import ait.cohort63.shop.model.entity.ConfirmationCode;
import ait.cohort63.shop.model.entity.User;
import ait.cohort63.shop.repository.ConfirmationCodeRepository;
import ait.cohort63.shop.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/confirm")
public class ConfirmController {
    private final ConfirmationCodeRepository codeRepository;
    private final UserRepository userRepository;

    public ConfirmController(ConfirmationCodeRepository codeRepository, UserRepository userRepository) {
        this.codeRepository = codeRepository;
        this.userRepository = userRepository;
    }


    @GetMapping
    public Response confirmRegistration(@RequestParam("code") String code) {
        ConfirmationCode confirmationCode = codeRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid confirmation code"));
        if (confirmationCode.getExpired().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Confirmation code expired");
        }
        User user = confirmationCode.getUser();
        user.setActive(true);
        userRepository.save(user);
        return new Response("Registration confirmed successfully");
    }
}
