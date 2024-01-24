package ru.clevertec.house.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.clevertec.house.constant.Type;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class HouseHistoryDto {

        private Long id;
        private Long houseId;
        private Long personId;
        private LocalDateTime date;
        private Type type;
}
