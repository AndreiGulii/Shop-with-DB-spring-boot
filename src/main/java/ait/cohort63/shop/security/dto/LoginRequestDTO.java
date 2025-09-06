package ait.cohort63.shop.security.dto;

// record sozdaiot avtomaticheski getteri setteri equals i hashkod a tak je to string dlea DTO
public record LoginRequestDTO (String username, String password) {}
