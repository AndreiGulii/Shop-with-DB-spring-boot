package ait.cohort63.shop.security.service;

import ait.cohort63.shop.model.entity.Role;
import ait.cohort63.shop.model.entity.User;
import ait.cohort63.shop.repository.RoleRepository;
import ait.cohort63.shop.security.AuthInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class TokenService {
    private final RestTemplateBuilderConfigurer restTemplateBuilderConfigurer;
    private RoleRepository roleRepository;
    private SecretKey accessKey;
    private SecretKey refreshKey;

    public TokenService(RoleRepository roleRepository,
                        @Value("${key.access}") String accessSecretPhrase,
                        @Value("${key.refresh}") String refreshSecretPhrase, RestTemplateBuilderConfigurer restTemplateBuilderConfigurer) {
        this.roleRepository = roleRepository;
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecretPhrase));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecretPhrase));
        this.restTemplateBuilderConfigurer = restTemplateBuilderConfigurer;
    }

    public String generateAccessToken(UserDetails user) {

        //Zadiom vremea dejstvija tokena
        Instant now = Instant.now();
        Instant expiration = now.plus(120, ChronoUnit.SECONDS);
        //
        Date expirationDate = Date.from(expiration);
        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(accessKey)
                .claim("roles", user.getAuthorities())
                .claim("name",  user.getUsername())
                .compact();
    }

    public String generateRefreshToken(UserDetails user) {
        Instant now = Instant.now();
        Instant expiration = now.plus(30, ChronoUnit.DAYS);
        Date expirationDate = Date.from(expiration);
        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(refreshKey)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, accessKey);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, refreshKey);
    }

    private boolean validateToken(String token, SecretKey secretKey) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaimsFromToken(String token, SecretKey secretKey) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims getAccessClaims(String accessToken) {
        return getClaimsFromToken(accessToken, accessKey);
    }

    public Claims getRefreshClaims(String refreshToken) {
        return getClaimsFromToken(refreshToken, refreshKey);
    }

    public AuthInfo mapClaimsToAuthInfo(Claims claims) {
        // Imea polizovatelea (username)
        String username = claims.getSubject();
        //Roli polizovatelea (roles)
        @SuppressWarnings("uncheked")
        List<Map<String,String>> roles = claims.get("roles", List.class);

        Set<Role> authority = roles.stream()
                .map(m-> m.get("authority"))
                .map(str -> roleRepository.findByTitle(str).orElseThrow(RuntimeException::new))
                .collect(Collectors.toSet());
        return new AuthInfo(username, authority);
    }
}
