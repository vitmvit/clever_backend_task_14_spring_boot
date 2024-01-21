package ru.clevertec.house.util;

import lombok.Builder;
import ru.clevertec.house.constant.Type;
import ru.clevertec.house.model.dto.HouseHistoryDto;
import ru.clevertec.house.model.entity.HouseHistory;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(setterPrefix = "with")
public class HouseHistoryTestBuilder {

    @Builder.Default
    LocalDateTime date = LocalDateTime.of(2024, 1, 22, 5, 34, 45, 23);
    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private Long houseId = 2L;
    @Builder.Default
    private Long personId = 1L;
    ;
    @Builder.Default
    private Type type = Type.OWNER;

    public HouseHistory buildHouseHistory() {
        var houseHistory = new HouseHistory(houseId, personId, date, type);
        houseHistory.setId(id);
        return houseHistory;
    }

    public HouseHistoryDto buildHouseHistoryDto() {
        return new HouseHistoryDto(id, houseId, personId, date, type);
    }

    public UUID getUuid() {
        return UUID.fromString("8a131fbc-38bf-4689-8f0b-958cef82a3ef");
    }
}