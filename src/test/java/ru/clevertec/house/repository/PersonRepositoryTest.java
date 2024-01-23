package ru.clevertec.house.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.house.model.entity.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void findPersonByUuidShouldReturnExpectedHouse() {
        UUID uuid = UUID.fromString("faa3c8d8-b6f8-4100-b253-3cd453a03da7");

        Optional<Person> actual = personRepository.findPersonByUuid(uuid);

        assertTrue(actual.isPresent());
        assertEquals(uuid, actual.get().getUuid());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Wilson, 1",
            "a, 1",
            "123, 0",
            "null, 0"
    }, ignoreLeadingAndTrailingWhitespace = true)
    void findByCityContainingShouldReturnExpectedHouseList(String expected, int expectedSize) {
        List<Person> actual = personRepository.findBySurnameContaining(expected);

        assertEquals(expectedSize, actual.size());
    }

    @Test
    void findAllHousesByIdShouldReturnExpectedHouseList() {
        List<Long> idList = List.of(1L, 2L);

        List<Person> actual = personRepository.findAllPersonsById(idList);

        assertEquals(idList.size(), actual.size());
    }

    @Test
    @Transactional
    void deleteByUuid() {
        UUID uuid = UUID.fromString("1cd31719-2064-4f90-a909-d7dd3b880d1e");

        personRepository.deleteByUuid(uuid);

        assertEquals(Optional.empty(), personRepository.findPersonByUuid(uuid));
    }
}