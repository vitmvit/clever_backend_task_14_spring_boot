package ru.clevertec.house.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.model.dto.create.PersonCreateDto;
import ru.clevertec.house.model.dto.update.PersonUpdateDto;
import ru.clevertec.house.service.PersonService;

import java.util.List;
import java.util.UUID;

import static ru.clevertec.house.constant.Constant.LIMIT_DEFAULT;
import static ru.clevertec.house.constant.Constant.OFFSET_DEFAULT;

@RestController
@AllArgsConstructor
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    @GetMapping
    Page<PersonDto> getAll(@RequestParam(value = "offset", defaultValue = OFFSET_DEFAULT) Integer offset,
                           @RequestParam(value = "limit", defaultValue = LIMIT_DEFAULT) Integer limit) {
        return personService.getAll(offset, limit);
    }

    @GetMapping("/{uuid}")
    PersonDto getByUuid(@PathVariable("uuid") UUID uuid) {
        return personService.getByUUID(uuid);
    }

    @GetMapping("houses/{uuid}")
    List<HouseDto> getAllHouses(@PathVariable("uuid") UUID uuid) {
        return personService.getAllHouses(uuid);
    }

    @PostMapping
    PersonDto create(@RequestBody PersonCreateDto personCreateDto) {
        return personService.create(personCreateDto);
    }

    @PutMapping
    PersonDto update(@RequestBody PersonUpdateDto personUpdateDto) {
        return personService.update(personUpdateDto);
    }

    @DeleteMapping("/{uuid}")
    void delete(@PathVariable("uuid") UUID uuid) {
        personService.delete(uuid);
    }
}
