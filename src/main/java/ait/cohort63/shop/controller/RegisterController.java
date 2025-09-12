package ait.cohort63.shop.controller;

import ait.cohort63.shop.exeption_handling.Response;
import ait.cohort63.shop.model.dto.UserRegisterDTO;
import ait.cohort63.shop.service.interfaces.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    private Response register (@RequestBody UserRegisterDTO userRegisterDTO) {
        userService.registerUser(userRegisterDTO);
        return new Response("Registration is completed, please check your e-Mail");
    }
}
