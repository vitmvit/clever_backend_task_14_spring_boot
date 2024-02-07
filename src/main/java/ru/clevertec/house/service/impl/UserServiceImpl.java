package ru.clevertec.house.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.clevertec.house.repository.UserRepository;
import ru.clevertec.house.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Находит пользователя по логину.
     *
     * @param login Логин пользователя.
     * @return UserDetails с информацией о пользователе.
     */
    @Override
    public UserDetails findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
