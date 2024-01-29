package ru.clevertec.house.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.clevertec.house.exception.InvalidJwtException;
import ru.clevertec.house.model.dto.auth.SignUpDto;
import ru.clevertec.house.model.entity.User;
import ru.clevertec.house.repository.UserRepository;

import static ru.clevertec.house.constant.Constant.USERNAME_EXIST;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    /**
     * Загружает информацию о пользователе по его имени пользователя.
     *
     * @param username Имя пользователя.
     * @return UserDetails с информацией о пользователе.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository.findByLogin(username);
    }

    /**
     * Создает нового пользователя.
     *
     * @param data Данные для создания нового пользователя.
     * @return UserDetails с информацией о новом пользователе.
     * @throws InvalidJwtException Если пользователь с таким именем уже существует.
     */
    public UserDetails signUp(SignUpDto data) {
        if (repository.findByLogin(data.login()) != null) {
            throw new InvalidJwtException(USERNAME_EXIST);
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());
        return repository.save(newUser);
    }
}
