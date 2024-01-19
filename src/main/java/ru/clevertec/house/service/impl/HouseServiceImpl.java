package ru.clevertec.house.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.house.converter.HouseConverter;
import ru.clevertec.house.converter.PersonConverter;
import ru.clevertec.house.exception.EntityNotFoundException;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.model.dto.create.HouseCreateDto;
import ru.clevertec.house.model.dto.update.HouseUpdateDto;
import ru.clevertec.house.model.entity.House;
import ru.clevertec.house.repository.HouseRepository;
import ru.clevertec.house.service.HouseService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    private final HouseConverter houseConverter;
    private final PersonConverter personConverter;

    @Override
    public HouseDto getByUUID(UUID uuid) {
        return houseConverter.convert(houseRepository.findByUuid(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Page<HouseDto> getAll(Integer offset, Integer limit) {
        Page<House> housePage = houseRepository.findAll(PageRequest.of(offset, limit));
        return housePage.map(houseConverter::convert);
    }

    @Override
    public HouseDto create(HouseCreateDto dto) {
        try {
            var house = houseConverter.convert(dto);
            house.setUuid(UUID.randomUUID());
            return houseConverter.convert(houseRepository.save(house));
        } catch (Exception e) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public HouseDto update(HouseUpdateDto dto) {
        var house = houseRepository.findByUuid(dto.getUuid()).orElseThrow(EntityNotFoundException::new);
        houseConverter.merge(house, dto);
        return houseConverter.convert(houseRepository.save(house));
    }

    @Override
    public void delete(UUID uuid) {
        houseRepository.deleteByUuid(uuid);
    }

    @Override
    public List<PersonDto> getAllResidents(UUID uuid) {
        var house = houseRepository.findByUuid(uuid).orElseThrow(EntityNotFoundException::new);
        return house.getResidents().isEmpty()
                ? List.of()
                : house.getResidents().stream().map(personConverter::convert).collect(Collectors.toList());
    }
}