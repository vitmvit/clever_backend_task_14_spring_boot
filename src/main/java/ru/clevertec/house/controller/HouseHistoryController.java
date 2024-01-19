package ru.clevertec.house.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.house.model.entity.HouseHistory;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/history")
public class HouseHistoryController {

    @GetMapping("houses/{uuid}")
    List<HouseHistory> getAllHouseHistoryByPersonIdAndType(@PathVariable("uuid") UUID uuid) {
        return null;
    }
}
