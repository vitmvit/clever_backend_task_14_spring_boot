package ru.clevertec.house.model.dto.auth;

import ru.clevertec.house.constant.RoleName;

public record SignUpDto(
        String login,
        String password,
        RoleName role) {
}
