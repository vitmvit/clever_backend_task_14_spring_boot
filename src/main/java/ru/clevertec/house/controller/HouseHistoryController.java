package ru.clevertec.house.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.service.HouseHistoryService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/history")
public class HouseHistoryController {

    private final HouseHistoryService houseHistoryService;

    // GET для получения всех Person когда-либо проживавших в доме
    @GetMapping("residing/{uuid}")
    public ResponseEntity<List<PersonDto>> getAllPersonResidingInHouse(@PathVariable("uuid") UUID houseUuid) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(houseHistoryService.getAllPersonResidingInHouse(houseUuid));
    }

    // GET для получения всех Person когда-либо владевших домом
    @GetMapping("owned/{uuid}")
    public ResponseEntity<List<PersonDto>> getAllPersonOwnedHouse(@PathVariable("uuid") UUID houseUuid) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(houseHistoryService.getAllPersonOwnedHouse(houseUuid));
    }

    // GET для получения всех House где проживал Person
    @GetMapping("resided/{uuid}")
    public ResponseEntity<List<HouseDto>> getAllHousesByPerson(@PathVariable("uuid") UUID personUuid) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(houseHistoryService.getAllHousesByPerson(personUuid));
    }

    // GET для получения всех House которыми когда-либо владел Person
    @GetMapping("belonged/{uuid}")
    public ResponseEntity<List<HouseDto>> getAllHousesOwnedByPerson(@PathVariable("uuid") UUID personUuid) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(houseHistoryService.getAllHousesOwnedByPerson(personUuid));
    }
}
