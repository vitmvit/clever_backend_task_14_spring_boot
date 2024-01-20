package ru.clevertec.house.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.clevertec.house.constant.Type;

import java.time.LocalDateTime;
import java.util.UUID;

public record HouseHistoryDto(

        Long id,
        UUID uuid,
        Long houseId,
        Long personId,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        LocalDateTime date,
        Type type
) {
}
