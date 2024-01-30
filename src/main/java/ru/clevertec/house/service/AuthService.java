package ru.clevertec.house.service;

import ru.clevertec.house.model.dto.auth.JwtDto;
import ru.clevertec.house.model.dto.auth.SignInDto;
import ru.clevertec.house.model.dto.auth.SignUpDto;

public interface AuthService {

    JwtDto signUp(SignUpDto dto);

    JwtDto signIn(SignInDto dto);
}
