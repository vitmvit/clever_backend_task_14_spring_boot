package ru.clevertec.house.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.house.model.entity.House;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    Optional<House> findHouseByUuid(UUID uuid);

    default List<House> findAllHousesById(List<Long> list) {
        return list.stream().map(p -> findById(p).orElseThrow(EntityNotFoundException::new)).collect(Collectors.toList());
    }

    List<House> findByCityContaining(String city);

    void deleteHouseByUuid(UUID uuid);
}
