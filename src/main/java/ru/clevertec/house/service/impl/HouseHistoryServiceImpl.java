package ru.clevertec.house.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.house.converter.HouseHistoryConverter;
import ru.clevertec.house.converter.PersonConverter;
import ru.clevertec.house.model.dto.HouseHistoryDto;
import ru.clevertec.house.repository.HouseHistoryRepository;
import ru.clevertec.house.repository.HouseRepository;
import ru.clevertec.house.service.HouseHistoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HouseHistoryServiceImpl implements HouseHistoryService {

    private final HouseRepository houseRepository;
    private final HouseHistoryRepository houseHistoryRepository;
    private final PersonConverter personConverter;
    private final HouseHistoryConverter houseHistoryConverter;

    @Override
    public List<HouseHistoryDto> getAll1() {

        var personList = houseHistoryRepository.findAll();
        return personList.isEmpty()
                ? List.of()
                : personList.stream().map(houseHistoryConverter::convert).collect(Collectors.toList());
    }

//    @Override
//    public List<PersonDto> getAll1(UUID uuid) {
//        var houseId = houseRepository.findHouseByUuid(uuid).orElseThrow(EntityNotFoundException::new).getId();
//        var personIdList = houseHistoryRepository.findPersonIdByHouseIdAndType(houseId, Type.TENANT);
//        var personList = houseHistoryRepository.findPersonIdByHouseIdAndType(houseId, Type.TENANT);
//        return personList.isEmpty()
//                ? List.of()
//                : personList.stream().map(personConverter::convert).collect(Collectors.toList());
//    }
}

