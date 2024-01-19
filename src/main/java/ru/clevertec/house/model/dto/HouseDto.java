package ru.clevertec.house.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.clevertec.house.constant.Type;

import java.time.LocalDateTime;

public record HouseDto(

        Long id,
        Long houseId,
        Long personId,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        LocalDateTime date,
        Type type
) {
}
