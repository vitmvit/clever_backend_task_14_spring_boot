package ru.clevertec.house.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.house.constant.Type;
import ru.clevertec.house.converter.HouseConverter;
import ru.clevertec.house.converter.PersonConverter;
import ru.clevertec.house.exception.EntityNotFoundException;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.repository.HouseHistoryRepository;
import ru.clevertec.house.repository.HouseRepository;
import ru.clevertec.house.repository.PersonRepository;
import ru.clevertec.house.service.HouseHistoryService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class HouseHistoryServiceImpl implements HouseHistoryService {

    private final HouseHistoryRepository houseHistoryRepository;
    private final HouseRepository houseRepository;
    private final HouseConverter houseConverter;
    private final PersonRepository personRepository;
    private final PersonConverter personConverter;


    @Override
    public List<PersonDto> getAllPersonResidingInHouse(UUID houseUuid) {
        var house = houseRepository.findHouseByUuid(houseUuid).orElseThrow(EntityNotFoundException::new);
        var listPersonId = houseHistoryRepository.findAllPersonIdByHouseIdAndType(house.getId(), Type.TENANT);
        return personRepository.findAllPersonsById(listPersonId.stream()
                        .toList())
                .stream()
                .map(personConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonDto> getAllPersonOwnedHouse(UUID houseUuid) {
        var house = houseRepository.findHouseByUuid(houseUuid).orElseThrow(EntityNotFoundException::new);
        var listPersonId = houseHistoryRepository.findAllPersonIdByHouseIdAndType(house.getId(), Type.OWNER);
        return personRepository.findAllPersonsById(listPersonId.stream()
                        .toList())
                .stream()
                .map(personConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<HouseDto> getAllHousesByPerson(UUID personUuid) {
        var person = personRepository.findPersonByUuid(personUuid).orElseThrow(EntityNotFoundException::new);
        var listPHouseId = houseHistoryRepository.findAllHouseIdByPersonIdAndType(person.getId(), Type.TENANT);
        return houseRepository.findAllHousesById(listPHouseId.stream()
                        .toList())
                .stream()
                .map(houseConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<HouseDto> getAllHousesOwnedByPerson(UUID personUuid) {
        var person = personRepository.findPersonByUuid(personUuid).orElseThrow(EntityNotFoundException::new);
        var listPHouseId = houseHistoryRepository.findAllHouseIdByPersonIdAndType(person.getId(), Type.OWNER);
        return houseRepository.findAllHousesById(listPHouseId.stream()
                        .toList())
                .stream()
                .map(houseConverter::convert)
                .collect(Collectors.toList());
    }
}

