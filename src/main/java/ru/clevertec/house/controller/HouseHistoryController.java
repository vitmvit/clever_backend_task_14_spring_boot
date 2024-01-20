package ru.clevertec.house.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.house.model.dto.HouseHistoryDto;
import ru.clevertec.house.service.HouseHistoryService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/history")
public class HouseHistoryController {

    private final HouseHistoryService houseHistoryService;

//    @GetMapping("persons/house/{uuid}")
//    List<PersonDto> getAllResidents(@PathVariable("uuid") UUID uuid) {
//        return houseHistoryService.getAll1(uuid);
//    }

    @GetMapping
    List<HouseHistoryDto> getAll() {
        return houseHistoryService.getAll1();
    }
//    GET для получения всех Person, когда-либо проживавших в доме
//    нужен uuid дома
//    далее uuid -> id
//    далее по id и type(tenant) найти всех person в history

//    GET для получения всех Person, когда-либо владевших домом
//    нужен uuid дома
//    далее uuid -> id
//    далее по id и type(owner) найти всех person в history

//    GET для получения всех House, где проживал Person:
//    нужен uuid person
//    далее uuid -> id
//    далее по id и type(tenant) найти всех house в history

//    GET для получения всех House, которыми когда-либо владел Person:
//    нужен uuid person
//    далее uuid -> id
//    далее по id и type(owner) найти всех house в history

//    @GetMapping("houses/{uuid}")
//    List<HouseHistory> getAllHouseHistoryByPersonIdAndType(@PathVariable("uuid") UUID uuid) {
//        return null;
//    }
}
