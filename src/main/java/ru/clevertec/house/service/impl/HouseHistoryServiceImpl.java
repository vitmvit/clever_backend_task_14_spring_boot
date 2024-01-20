package ru.clevertec.house.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.house.model.entity.HouseHistory;
import ru.clevertec.house.repository.HouseHistoryRepository;
import ru.clevertec.house.service.HouseHistoryService;

import java.util.List;

@Service
@AllArgsConstructor
public class HouseHistoryServiceImpl implements HouseHistoryService {

    private final HouseHistoryRepository houseHistoryRepository;


    @Override
    public List<HouseHistory> findAll() {
        return houseHistoryRepository.findAll();
    }
}

