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
import ru.clevertec.house.service.HouseService;
import ru.clevertec.house.service.PersonService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

    private final PersonService personService;
    private final HouseService houseService;

    @GetMapping("/person/{surname}")
    public ResponseEntity<List<PersonDto>> searchPersonBySurname(@PathVariable("surname") String surname) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personService.searchBySurname(surname));
    }

    @GetMapping("/house/{city}")
    public ResponseEntity<List<HouseDto>> searchHouseByCity(@PathVariable("city") String city) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(houseService.searchByCity(city));
    }
}
