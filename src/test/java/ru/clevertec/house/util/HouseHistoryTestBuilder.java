package ru.clevertec.house.util;

import lombok.Builder;
import ru.clevertec.house.constant.Type;

import java.time.LocalDateTime;
import java.util.List;
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

    @Builder.Default
    private Type type = Type.TENANT;

    public UUID getUuid() {
        return UUID.fromString("8a131fbc-38bf-4689-8f0b-958cef82a3ef");
    }

    public List<Long> getListLong() {
        List<Long> list = List.of(1L, 2L);
        return list;
    }
}