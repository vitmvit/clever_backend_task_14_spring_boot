package ru.clevertec.house.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDetails findByLogin(String login);
}
