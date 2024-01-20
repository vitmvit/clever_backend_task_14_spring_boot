package ru.clevertec.house.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.house.model.entity.HouseHistory;
import ru.clevertec.house.service.HouseHistoryService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/history")
public class HouseHistoryController {

    private final HouseHistoryService houseHistoryService;

    @GetMapping
    List<HouseHistory> getAll() {
        return houseHistoryService.findAll();
    }
}
