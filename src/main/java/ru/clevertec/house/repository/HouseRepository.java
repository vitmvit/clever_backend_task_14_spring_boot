package ru.clevertec.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.house.model.entity.House;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    Optional<House> findHouseByUuid(UUID uuid);

    void deleteHouseByUuid(UUID uuid);
}
