package ru.clevertec.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import ru.clevertec.house.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByLogin(String login);
}
