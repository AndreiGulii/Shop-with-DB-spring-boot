package ait.cohort63.shop.security.dto;

import java.util.Objects;

public class TokenResponseDTO {

    private String accessToken;
    private String refreshToken;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        TokenResponseDTO that = (TokenResponseDTO) o;
        return Objects.equals(accessToken, that.accessToken) && Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(accessToken);
        result = 31 * result + Objects.hashCode(refreshToken);
        return result;
    }

    public  TokenResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;


    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public TokenResponseDTO() {
    }

    @Override
    public String toString() {
        return String.format("Token Response -> accessToken: %s, refreshToken: %s", accessToken, refreshToken);
    }
}
