package ru.clevertec.house.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.house.constant.Type;
import ru.clevertec.house.converter.HouseConverter;
import ru.clevertec.house.converter.PersonConverter;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.model.entity.House;
import ru.clevertec.house.model.entity.Person;
import ru.clevertec.house.repository.HouseHistoryRepository;
import ru.clevertec.house.repository.HouseRepository;
import ru.clevertec.house.repository.PersonRepository;
import ru.clevertec.house.service.impl.HouseHistoryServiceImpl;
import ru.clevertec.house.util.HouseHistoryTestBuilder;
import ru.clevertec.house.util.HouseTestBuilder;
import ru.clevertec.house.util.PersonTestBuilder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HouseHistoryServiceTest {

    @Mock
    private HouseHistoryRepository houseHistoryRepository;

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonConverter personConverter;
    @Mock
    private HouseConverter houseConverter;

    @InjectMocks
    private HouseHistoryServiceImpl houseHistoryService;

    @Test
    public void getAllPersonResidingInHouseShouldReturnListPersonDto() {
        House house = HouseTestBuilder.builder().build().buildHouse();
        UUID houseUuid = house.getUuid();
        List<Long> listPersonId = HouseHistoryTestBuilder.builder().build().getListLong();
        Set<Long> setPersonId = new TreeSet<>(listPersonId);
        List<Person> listPerson = PersonTestBuilder.builder().build().getListPerson();

        when(houseRepository.findHouseByUuid(houseUuid)).thenReturn(Optional.of(house));
        when(houseHistoryRepository.findAllPersonIdByHouseIdAndType(house.getId(), Type.OWNER)).thenReturn(setPersonId);
        when(personRepository.findAllPersonsById(listPersonId)).thenReturn(listPerson);
        when(personConverter.convert(any(Person.class))).thenReturn(new PersonDto());

        List<PersonDto> result = houseHistoryService.getAllPersonOwnedHouse(houseUuid);

        assertEquals(2, result.size());
        verify(houseRepository).findHouseByUuid(houseUuid);
        verify(houseHistoryRepository).findAllPersonIdByHouseIdAndType(house.getId(), Type.OWNER);
        verify(personRepository).findAllPersonsById(listPersonId);
        verify(personConverter, times(2)).convert(any(Person.class));
    }

    @Test
    public void getAllPersonOwnedHouseShouldReturnListPersonDto() {
        House house = HouseTestBuilder.builder().build().buildHouse();
        UUID houseUuid = house.getUuid();
        List<Long> listPersonId = HouseHistoryTestBuilder.builder().build().getListLong();
        Set<Long> setPersonId = new TreeSet<>(listPersonId);
        List<Person> listPerson = PersonTestBuilder.builder().build().getListPerson();

        when(houseRepository.findHouseByUuid(houseUuid)).thenReturn(Optional.of(house));
        when(houseHistoryRepository.findAllPersonIdByHouseIdAndType(house.getId(), Type.TENANT)).thenReturn(setPersonId);
        when(personRepository.findAllPersonsById(listPersonId)).thenReturn(listPerson);
        when(personConverter.convert(any(Person.class))).thenReturn(new PersonDto());

        List<PersonDto> result = houseHistoryService.getAllPersonResidingInHouse(houseUuid);

        assertEquals(2, result.size());
        verify(houseRepository).findHouseByUuid(houseUuid);
        verify(houseHistoryRepository).findAllPersonIdByHouseIdAndType(house.getId(), Type.TENANT);
        verify(personRepository).findAllPersonsById(listPersonId);
        verify(personConverter, times(2)).convert(any(Person.class));
    }

    @Test
    void getAllHousesByPersonShouldReturnListHouseDto() {
        Person person = PersonTestBuilder.builder().build().buildPerson();
        UUID personUuid = person.getUuid();
        List<Long> listHouseId = HouseHistoryTestBuilder.builder().build().getListLong();
        Set<Long> setHouseId = new TreeSet<>(listHouseId);
        List<House> listHouse = HouseTestBuilder.builder().build().getListHouse();

        when(personRepository.findPersonByUuid(personUuid)).thenReturn(Optional.of(person));
        when(houseHistoryRepository.findAllHouseIdByPersonIdAndType(person.getId(), Type.TENANT)).thenReturn(setHouseId);
        when(houseRepository.findAllHousesById(listHouseId)).thenReturn(listHouse);
        when(houseConverter.convert(any(House.class))).thenReturn(new HouseDto());

        List<HouseDto> result = houseHistoryService.getAllHousesByPerson(personUuid);

        assertEquals(2, result.size());
        verify(personRepository).findPersonByUuid(personUuid);
        verify(houseHistoryRepository).findAllHouseIdByPersonIdAndType(person.getId(), Type.TENANT);
        verify(houseRepository).findAllHousesById(listHouseId);
        verify(houseConverter, times(2)).convert(any(House.class));
    }

    @Test
    void getAllHousesOwnedByPerson() {
        Person person = PersonTestBuilder.builder().build().buildPerson();
        UUID personUuid = person.getUuid();
        List<Long> listHouseId = HouseHistoryTestBuilder.builder().build().getListLong();
        Set<Long> setHouseId = new TreeSet<>(listHouseId);
        List<House> listHouse = HouseTestBuilder.builder().build().getListHouse();

        when(personRepository.findPersonByUuid(personUuid)).thenReturn(Optional.of(person));
        when(houseHistoryRepository.findAllHouseIdByPersonIdAndType(person.getId(), Type.OWNER)).thenReturn(setHouseId);
        when(houseRepository.findAllHousesById(listHouseId)).thenReturn(listHouse);
        when(houseConverter.convert(any(House.class))).thenReturn(new HouseDto());

        List<HouseDto> result = houseHistoryService.getAllHousesOwnedByPerson(personUuid);

        assertEquals(2, result.size());
        verify(personRepository).findPersonByUuid(personUuid);
        verify(houseHistoryRepository).findAllHouseIdByPersonIdAndType(person.getId(), Type.OWNER);
        verify(houseRepository).findAllHousesById(listHouseId);
        verify(houseConverter, times(2)).convert(any(House.class));
    }
}
