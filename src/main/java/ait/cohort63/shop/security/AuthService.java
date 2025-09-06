package ait.cohort63.shop.security;

import ait.cohort63.shop.security.dto.LoginRequestDTO;
import ait.cohort63.shop.security.dto.RefreshRequestDTO;
import ait.cohort63.shop.security.dto.TokenResponseDTO;
import ait.cohort63.shop.security.service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UserDetailsService userService;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Map<String, String> refreshStorage;

    public AuthService(UserDetailsService userService, TokenService tokenService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.refreshStorage = new HashMap<>();
    }

    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) throws AuthException {

        String username = loginRequestDTO.username();

        UserDetails foundUser = userService.loadUserByUsername(username);

        if (passwordEncoder.matches(loginRequestDTO.password(), foundUser.getPassword())) {
            String accessToken = tokenService.generateAccessToken(foundUser);
            String refreshToken = tokenService.generateRefreshToken(foundUser);

            refreshStorage.put(username, refreshToken);

            return new TokenResponseDTO(accessToken, refreshToken);
        }
        throw new AuthException("Incorrect Login and / or password");
    }

    public TokenResponseDTO refreshAccessToken(RefreshRequestDTO refreshRequestDTO) throws AuthException {

        String refreshToken = refreshRequestDTO.getRefresfToken();

        //Provereaiem Token na validnosti
        boolean isValid = tokenService.validateRefreshToken(refreshToken);

        //poluchaiem informatsiiu o polizovatele iz tokena
        Claims refreshClaims = tokenService.getRefreshClaims(refreshToken);

        String username = refreshClaims.getSubject();

        //Poluchaiem token po username iz hranilisha tokenov
        String savedToken = refreshStorage.get(username);

        boolean isSaved = savedToken != null  && savedToken.equals(refreshToken);

        if(isValid && isSaved) {
            UserDetails foundUser = userService.loadUserByUsername(username);
            String accessToken = tokenService.generateAccessToken(foundUser);

            return new TokenResponseDTO(accessToken, refreshToken);
        }
        throw new AuthException("Incorrect refresh token. Re-login please");
    }
}
