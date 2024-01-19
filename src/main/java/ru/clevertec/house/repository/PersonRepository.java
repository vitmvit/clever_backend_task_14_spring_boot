package ru.clevertec.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.house.model.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
