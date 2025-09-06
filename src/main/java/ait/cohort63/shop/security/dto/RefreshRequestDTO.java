package ait.cohort63.shop.security.dto;

import java.util.Objects;

public class RefreshRequestDTO {
    private String refreshToken;

    @Override
    public String toString() {
        return "Refresh request -> refresh token: " + refreshToken;
    }

    public String getRefresfToken() {
        return refreshToken;
    }

    public void setRefresfToken(String refresfToken) {
        this.refreshToken = refresfToken;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        RefreshRequestDTO that = (RefreshRequestDTO) o;
        return Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(refreshToken);
    }
}
