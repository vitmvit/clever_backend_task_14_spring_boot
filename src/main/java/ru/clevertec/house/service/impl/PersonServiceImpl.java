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
import ru.clevertec.house.model.dto.create.PersonCreateDto;
import ru.clevertec.house.model.dto.update.PersonUpdateDto;
import ru.clevertec.house.model.entity.Person;
import ru.clevertec.house.repository.PersonRepository;
import ru.clevertec.house.service.PersonService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonConverter personConverter;
    private final HouseConverter houseConverter;

    @Override
    public PersonDto getByUUID(UUID uuid) {
        return personConverter.convert(personRepository.findByUuid(uuid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Page<PersonDto> getAll(Integer offset, Integer limit) {
        Page<Person> personPage = personRepository.findAll(PageRequest.of(offset, limit));
        return personPage.map(personConverter::convert);
    }

    @Override
    public PersonDto create(PersonCreateDto dto) {
        try {
            var person = personConverter.convert(dto);
            return personConverter.convert(personRepository.save(person));
        } catch (Exception e) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public PersonDto update(PersonUpdateDto dto) {
        var person = personRepository.findByUuid(dto.getUuid()).orElseThrow(EntityNotFoundException::new);
        personConverter.merge(person, dto);
        return personConverter.convert(personRepository.save(person));
    }

    @Override
    public void delete(UUID uuid) {
        personRepository.deleteByUuid(uuid);
    }

    @Override
    public List<HouseDto> getAllHouses(UUID uuid) {
        var person = personRepository.findByUuid(uuid).orElseThrow(EntityNotFoundException::new);
        return person.getHouses().isEmpty()
                ? List.of()
                : person.getHouses().stream().map(houseConverter::convert).collect(Collectors.toList());
    }
}
