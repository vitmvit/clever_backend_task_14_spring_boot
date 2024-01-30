package ru.clevertec.house.constant;

import lombok.Getter;

@Getter
public enum RoleName {
    ADMIN("admin"),
    USER("user");

    private final String role;

    RoleName(String role) {
        this.role = role;
    }
}
