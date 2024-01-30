package ru.clevertec.house.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.clevertec.house.config.PostgresSqlContainerInitializer;
import ru.clevertec.house.config.auth.TokenProvider;
import ru.clevertec.house.constant.Type;
import ru.clevertec.house.model.entity.HouseHistory;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@RequiredArgsConstructor
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HouseHistoryRepositoryTest extends PostgresSqlContainerInitializer {

    @Autowired
    private HouseHistoryRepository houseHistoryRepository;

    @MockBean
    private TokenProvider tokenProvider;

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
