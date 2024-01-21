package ru.clevertec.house.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.house.model.entity.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findPersonByUuid(UUID uuid);

    default List<Person> findAllPersonsById(List<Long> list) {
        return list.stream().map(p -> findById(p).orElseThrow(EntityNotFoundException::new)).collect(Collectors.toList());
    }

    void deleteByUuid(UUID uuid);
}
