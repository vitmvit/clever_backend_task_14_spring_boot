package ru.clevertec.house.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.house.config.PostgresSqlContainerInitializer;
import ru.clevertec.house.config.auth.TokenProvider;
import ru.clevertec.house.model.entity.Person;
import ru.clevertec.house.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@RequiredArgsConstructor
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest extends PostgresSqlContainerInitializer {

    @Autowired
    private PersonRepository personRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenProvider tokenProvider;

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
