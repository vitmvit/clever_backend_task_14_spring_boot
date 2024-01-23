package ru.clevertec.house.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.clevertec.house.model.entity.House;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.clevertec.house.constant.Constant.FRAGMENT_CITY;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseRepositoryTest {

    @Autowired
    private HouseRepository houseRepository;

    @Test
    void findPersonByUuidShouldReturnExpectedHouse() {
        Optional<House> actual = houseRepository.findHouseByUuid(UUID.fromString("6d316b83-126e-4090-bc81-4125a98923c0"));
        assertEquals(UUID.fromString("6d316b83-126e-4090-bc81-4125a98923c0"), actual.get().getUuid());
    }

    @Test
    void findByCityContainingShouldReturnExpectedHouseList() {
        List<House> resultList = houseRepository.findByCityContaining(FRAGMENT_CITY);
        assertEquals(1, resultList.size());
    }

//    @Test
//    void findAllHousesByIdShouldReturnExpectedHouseList() {
//        List<House> resultList = houseRepository.findAllHousesById(List.of(1L));
//        assertEquals(1, resultList.size());
//    }

//    @Test
//    void hhh() {
//        assertDoesNotThrow(() -> houseRepository.deleteHouseByUuid(UUID.fromString("6d316b83-126e-4090-bc81-4125a98923c0")));
//    }
}
