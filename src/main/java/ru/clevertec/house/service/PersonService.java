package ru.clevertec.house.service;

import org.springframework.data.domain.Page;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.model.dto.create.PersonCreateDto;
import ru.clevertec.house.model.dto.update.PersonUpdateDto;

import java.util.List;
import java.util.UUID;

public interface PersonService {

    PersonDto getByUUID(UUID uuid);

    Page<PersonDto> getAll(Integer offset, Integer limit);

    PersonDto create(PersonCreateDto dto);

    PersonDto update(PersonUpdateDto dto);

    void delete(UUID uuid);

    List<HouseDto> getAllHouses(UUID uuid);
}
