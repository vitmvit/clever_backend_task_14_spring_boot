package ru.clevertec.house.service;

import ru.clevertec.house.model.entity.HouseHistory;

import java.util.List;

public interface HouseHistoryService {

    List<HouseHistory> findAll();
}
