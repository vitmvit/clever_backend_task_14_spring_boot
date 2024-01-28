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
import ru.clevertec.house.PostgresSqlContainerInitializer;
import ru.clevertec.house.model.entity.House;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseRepositoryTest extends PostgresSqlContainerInitializer {

    @Autowired
    private HouseRepository houseRepository;

    @Test
    void findPersonByUuidShouldReturnExpectedHouse() {
        UUID uuid = UUID.fromString("9dd06f39-dba5-4533-8472-f1d7be435491");

        Optional<House> actual = houseRepository.findHouseByUuid(uuid);

        assertTrue(actual.isPresent());
        assertEquals(uuid, actual.get().getUuid());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "City, 5",
            "One, 1",
            "123, 0",
            "null, 0"
    }, ignoreLeadingAndTrailingWhitespace = true)
    void findByCityContainingShouldReturnExpectedHouseList(String expected, int expectedSize) {
        List<House> actual = houseRepository.findByCityContaining(expected);

        assertEquals(expectedSize, actual.size());
    }

    @Test
    void findAllHousesByIdShouldReturnExpectedHouseList() {
        List<Long> idList = List.of(1L, 2L);

        List<House> actual = houseRepository.findAllHousesById(idList);

        assertEquals(idList.size(), actual.size());
    }

    @Test
    @Transactional
    void deleteByUuid() {
        UUID uuid = UUID.fromString("5e9832cf-3a74-40ae-9dae-c70f47f08804");

        houseRepository.deleteHouseByUuid(uuid);

        assertEquals(Optional.empty(), houseRepository.findHouseByUuid(uuid));
    }
}
