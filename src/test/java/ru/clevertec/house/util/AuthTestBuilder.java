package ru.clevertec.house.util;

import lombok.Builder;
import ru.clevertec.house.constant.RoleName;
import ru.clevertec.house.model.dto.auth.JwtDto;
import ru.clevertec.house.model.dto.auth.SignInDto;
import ru.clevertec.house.model.dto.auth.SignUpDto;
import ru.clevertec.house.model.entity.User;

@Builder(setterPrefix = "with")
public class AuthTestBuilder {

    @Builder.Default
    private String login = "TestUser";

    @Builder.Default
    private String password = "TestUser";

    @Builder.Default
    private RoleName role = RoleName.ADMIN;


    public SignUpDto buildSignUpDto() {
        return new SignUpDto(login, password, role);
    }

    public SignInDto buildSignInDto() {
        return new SignInDto(login, password);
    }

    public User buildUser() {
        return new User(login, password, role);
    }

    public JwtDto buildJwtDto() {
        return new JwtDto("login");
    }
}
