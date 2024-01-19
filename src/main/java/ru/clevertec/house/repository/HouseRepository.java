package ru.clevertec.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.house.model.entity.House;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
}
