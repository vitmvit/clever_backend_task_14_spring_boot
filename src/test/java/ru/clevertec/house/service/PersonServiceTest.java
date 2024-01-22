package ru.clevertec.house.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.house.converter.HouseConverter;
import ru.clevertec.house.converter.PersonConverter;
import ru.clevertec.house.exception.EntityNotFoundException;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.model.dto.create.PersonCreateDto;
import ru.clevertec.house.model.dto.update.PersonUpdateDto;
import ru.clevertec.house.model.entity.House;
import ru.clevertec.house.model.entity.Person;
import ru.clevertec.house.model.entity.parent.BaseModel;
import ru.clevertec.house.model.entity.parent.UuidModel;
import ru.clevertec.house.repository.HouseRepository;
import ru.clevertec.house.repository.PersonRepository;
import ru.clevertec.house.service.impl.PersonServiceImpl;
import ru.clevertec.house.util.HouseTestBuilder;
import ru.clevertec.house.util.PersonTestBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.clevertec.house.constant.Constant.LIMIT;
import static ru.clevertec.house.constant.Constant.OFFSET;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private PersonConverter personConverter;

    @Mock
    private HouseConverter houseConverter;

    @InjectMocks
    private PersonServiceImpl personService;

    @Captor
    private ArgumentCaptor<Person> argumentCaptor;


    @Test
    void getByUuidShouldReturnExpectedPersonWhenFound() {
        Person expected = PersonTestBuilder.builder().build().buildPerson();
        PersonDto personDto = PersonTestBuilder.builder().build().buildPersonDto();
        UUID uuid = expected.getUuid();

        when(personRepository.findPersonByUuid(uuid)).thenReturn(Optional.of(expected));
        when(personConverter.convert(expected)).thenReturn(personDto);

        PersonDto actual = personService.getByUuid(uuid);

        assertThat(actual)
                .hasFieldOrPropertyWithValue(UuidModel.Fields.uuid, expected.getUuid())
                .hasFieldOrPropertyWithValue(Person.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Person.Fields.surname, expected.getSurname())
                .hasFieldOrPropertyWithValue(Person.Fields.sex, expected.getSex());
    }

    @Test
    void getByUuidShouldThrowEntityNotFoundExceptionWhenPersonNotFound() {
        var exception = assertThrows(Exception.class, () -> personService.getByUuid(null));
        assertEquals(exception.getClass(), EntityNotFoundException.class);
    }

    @Test
    void getAllShouldReturnExpectedListPersons() {
        var page = mock(Page.class);

        when(personRepository.findAll(any(PageRequest.class))).thenReturn(page);

        personService.getAll(OFFSET, LIMIT);

        verify(personRepository).findAll(any(PageRequest.class));
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void getAllShouldReturnEmptyPageWhenEmptyPagePersons() {
        when(personRepository.findAll(PageRequest.of(OFFSET, LIMIT))).thenReturn(Page.empty());

        Page<PersonDto> actualList = personService.getAll(OFFSET, LIMIT);

        assertEquals(0, actualList.getTotalElements());
        verify(personRepository, times(1)).findAll(PageRequest.of(OFFSET, LIMIT));
    }

    @Test
    void getAllHousesShouldReturnExpectedListHouseDto() {
        UUID uuid = PersonTestBuilder.builder().build().getUuid();
        Person person = PersonTestBuilder.builder().build().buildPerson();

        when(personRepository.findPersonByUuid(uuid)).thenReturn(Optional.of(person));

        List<HouseDto> result = personService.getAllHouses(uuid);

        assertEquals(2, result.size());
        verify(personRepository).findPersonByUuid(uuid);
    }

    @Test
    void createShouldInvokeRepositoryWithoutPersonId() {
        PersonCreateDto dto = PersonTestBuilder.builder().withId(null).build().buildPersonCreateDto();
        UUID homeUuid = dto.getHomeUuid();
        Person person = PersonTestBuilder.builder().withId(null).build().buildPerson();
        House house = HouseTestBuilder.builder().build().buildHouse();
        house.setUuid(homeUuid);

        when(houseRepository.findHouseByUuid(homeUuid)).thenReturn(Optional.of(house));
        when(personConverter.convert(dto)).thenReturn(person);
        when(personRepository.save(person)).thenReturn(person);

        personService.create(dto);

        verify(personRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).hasFieldOrPropertyWithValue(BaseModel.Fields.id, null);
        verify(houseRepository).findHouseByUuid(homeUuid);
        verify(personConverter).convert(dto);
        verify(personRepository).save(person);
    }


    @Test
    void updateShouldCallsMergeAndSaveWhenPersonFound() {
        PersonUpdateDto dto = PersonTestBuilder.builder().build().buildPersonUpdateDto();
        UUID uuid = dto.getUuid();
        Person person = PersonTestBuilder.builder().build().buildPerson();

        when(personRepository.findPersonByUuid(uuid)).thenReturn(Optional.of(person));
        personService.update(dto);

        verify(personRepository, times(1)).findPersonByUuid(uuid);
        verify(personConverter, times(1)).merge(argumentCaptor.capture(), eq(dto));
        assertSame(person, argumentCaptor.getValue());
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void updateShouldThrowEntityNotFoundExceptionWhenPersonNotFound() {
        PersonUpdateDto dto = PersonTestBuilder.builder().build().buildPersonUpdateDto();
        UUID uuid = dto.getUuid();

        when(personRepository.findPersonByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> personService.update(dto));
        verify(personRepository, times(1)).findPersonByUuid(uuid);
    }

    @Test
    void delete() {
        UUID uuid = HouseTestBuilder.builder().build().getUuid();
        personService.delete(uuid);
        verify(personRepository).deleteByUuid(uuid);
    }
}
