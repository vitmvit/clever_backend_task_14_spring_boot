package ru.clevertec.house.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.clevertec.house.config.auth.TokenProvider;
import ru.clevertec.house.exception.InvalidJwtException;
import ru.clevertec.house.model.dto.auth.JwtDto;
import ru.clevertec.house.model.dto.auth.SignInDto;
import ru.clevertec.house.model.dto.auth.SignUpDto;
import ru.clevertec.house.model.entity.User;
import ru.clevertec.house.repository.UserRepository;
import ru.clevertec.house.service.AuthService;

import static ru.clevertec.house.constant.Constant.USERNAME_NOT_EXIST;

@Service
public class AuthServiceImpl implements UserDetailsService, AuthService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository,
                           TokenProvider tokenProvider,
                           @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Загружает информацию о пользователе по его имени пользователя.
     *
     * @param username Имя пользователя.
     * @return UserDetails с информацией о пользователе.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByLogin(username);
    }

    /**
     * Создает нового пользователя.
     *
     * @param dto Данные для создания нового пользователя.
     * @return UserDetails с информацией о новом пользователе.
     * @throws InvalidJwtException Если пользователь с таким именем уже существует.
     */
    public JwtDto signUp(SignUpDto dto) {
        if (userRepository.existsByLogin(dto.login())) {
            throw new InvalidJwtException(USERNAME_NOT_EXIST);
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User newUser = new User(dto.login(), encryptedPassword, dto.role());
        userRepository.save(newUser);
        return buildJwt(dto.login(), dto.password());
    }

    public JwtDto signIn(SignInDto dto) {
        if (!userRepository.existsByLogin(dto.login())) {
            throw new InvalidJwtException(USERNAME_NOT_EXIST);
        }
        return buildJwt(dto.login(), dto.password());
    }

    private JwtDto buildJwt(String login, String password) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(login, password);
        var authUser = authenticationManager.authenticate(usernamePassword);
        var accessToken = tokenProvider.generateAccessToken((User) authUser.getPrincipal());
        return new JwtDto(accessToken);
    }
}
