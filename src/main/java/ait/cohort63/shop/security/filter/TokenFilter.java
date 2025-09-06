package ait.cohort63.shop.security.filter;

import ait.cohort63.shop.security.AuthInfo;
import ait.cohort63.shop.security.service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class TokenFilter extends GenericFilterBean {

    private final TokenService tokenService;

    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest =(HttpServletRequest) servletRequest;

        // Izvlekaiem token iz zagolovka Authorization
        String accessToken = getTokenFromRequest(httpServletRequest);

        if (accessToken != null && tokenService.validateAccessToken(accessToken)) {

            // Izvlekaiem informatsiiu o polizovatele iz tokena
            Claims claims = tokenService.getAccessClaims(accessToken);
            //Konvertiruiem Claims v AuthInfo (chtob Security mog rabotati s etim Obiektom
            AuthInfo authInfo=tokenService.mapClaimsToAuthInfo(claims);

            //Ustanavlevaiem polizovatelia kak autentifizirovanogo
            authInfo.setAuthenticated(true);

            // Pomesheiaiem obiekt AuthInfo v SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authInfo);
        }
        // Prodoljim tsepochiku filtrov
        filterChain.doFilter(servletRequest, servletResponse);
    }
    // Headers:
    //Authorisation: Bearer <token>
    private String getTokenFromRequest(HttpServletRequest httpServletRequest) {
        //Izvlekaiem znacheniie zagolovka Authorisation
        String bearerToken = httpServletRequest.getHeader("Authorization");

        // Proverim chto zagolovok ne pustoi i chto on nachinaietsa so slova "Bearer"
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            //Obrezaiem slovo "Bearer" v Headere i ostavleaiem chistii token
            return bearerToken.substring(7);
        }
        // Esli zagolovok pustoi ili ne nachinaietsa so slova "Bearer"
        return null;
    }
}
