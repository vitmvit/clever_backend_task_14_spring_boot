package ru.clevertec.house.listener;

import jakarta.persistence.PrePersist;
import ru.clevertec.house.model.entity.House;

import java.util.UUID;

public class PersonListener {

    @PrePersist
    public void persist(House house) {
        house.setUuid(UUID.randomUUID());
    }
}
