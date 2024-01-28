package ru.clevertec.house.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.clevertec.house.PostgresSqlContainerInitializer;
import ru.clevertec.house.constant.Type;
import ru.clevertec.house.model.entity.HouseHistory;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseHistoryRepositoryTest extends PostgresSqlContainerInitializer {

    @Autowired
    private HouseHistoryRepository houseHistoryRepository;

    @Test
    void findAllByHouseIdAndTypeShouldReturnExpectedListHouseHistory() {
        Long houseId = 1L;
        Type type = Type.OWNER;

        List<HouseHistory> houseHistoryList = houseHistoryRepository.findAllByHouseIdAndType(houseId, type);

        assertEquals(1, houseHistoryList.size());
    }

    @Test
    void findAllPersonIdByHouseIdAndTypeShouldReturnExpectedSetLong() {
        Long houseId = 1L;
        Type type = Type.OWNER;

        Set<Long> actual = houseHistoryRepository.findAllPersonIdByHouseIdAndType(houseId, type);

        assertEquals(1, actual.size());
    }

    @Test
    void findAllHouseIdByPersonIdAndTypeShouldReturnExpectedSetLong() {
        Long personId = 1L;
        Type type = Type.TENANT;

        Set<Long> actual = houseHistoryRepository.findAllHouseIdByPersonIdAndType(personId, type);

        assertEquals(1, actual.size());
    }
}
