package ru.clevertec.house.service;

import org.springframework.data.domain.Page;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.model.dto.create.HouseCreateDto;
import ru.clevertec.house.model.dto.update.HouseUpdateDto;

import java.util.List;
import java.util.UUID;

public interface HouseService {

    HouseDto getByUuid(UUID uuid);

    Page<HouseDto> getAll(Integer offset, Integer limit);

    List<HouseDto> searchByCity(String city);

    HouseDto create(HouseCreateDto dto);

    HouseDto update(HouseUpdateDto dto);

    HouseDto patch(HouseUpdateDto houseUpdateDto);

    void delete(UUID uuid);

    List<PersonDto> getAllResidents(UUID uuid);
}
