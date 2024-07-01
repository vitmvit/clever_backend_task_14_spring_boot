package ru.clevertec.house.model.dto.auth;

public record SignInDto(
        String login,
        String password) {
}
