package ru.clevertec.house.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.model.dto.create.HouseCreateDto;
import ru.clevertec.house.model.dto.update.HouseUpdateDto;
import ru.clevertec.house.service.HouseService;

import java.util.List;
import java.util.UUID;

import static ru.clevertec.house.constant.Constant.LIMIT_DEFAULT;
import static ru.clevertec.house.constant.Constant.OFFSET_DEFAULT;

@RestController
@AllArgsConstructor
@RequestMapping("/api/houses")
public class HouseController {

    private final HouseService houseService;

    @GetMapping
    Page<HouseDto> getAll(@RequestParam(value = "offset", defaultValue = OFFSET_DEFAULT) Integer offset,
                          @RequestParam(value = "limit", defaultValue = LIMIT_DEFAULT) Integer limit) {
        return houseService.getAll(offset, limit);
    }

    @GetMapping("/{uuid}")
    HouseDto getByUuid(@PathVariable("uuid") UUID uuid) {
        return houseService.getByUUID(uuid);
    }

    @GetMapping("residents/{uuid}")
    List<PersonDto> getAllResidents(@PathVariable("uuid") UUID uuid) {
        return houseService.getAllResidents(uuid);
    }

    @PostMapping
    HouseDto create(@RequestBody HouseCreateDto houseCreateDto) {
        return houseService.create(houseCreateDto);
    }

    @PutMapping
    HouseDto update(@RequestBody HouseUpdateDto houseUpdateDto) {
        return houseService.update(houseUpdateDto);
    }

    @DeleteMapping("/{uuid}")
    void delete(@PathVariable("uuid") UUID uuid) {
        houseService.delete(uuid);
    }
}
