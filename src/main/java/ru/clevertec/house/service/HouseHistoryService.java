package ru.clevertec.house.service;

import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;

import java.util.List;
import java.util.UUID;

public interface HouseHistoryService {

    List<PersonDto> getAllPersonResidingInHouse(UUID houseUuid);

    List<PersonDto> getAllPersonOwnedHouse(UUID houseUuid);

    List<HouseDto> getAllHousesByPerson(UUID personUuid);

    List<HouseDto> getAllHousesOwnedByPerson(UUID personUuid);
}
