package ru.clevertec.house.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record HouseHistoryDto(

        UUID uuid,
        String area,
        String country,
        String city,
        String street,
        int number,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        LocalDateTime createDate
) {
}
