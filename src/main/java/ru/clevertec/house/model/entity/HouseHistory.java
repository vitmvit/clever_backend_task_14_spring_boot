package ru.clevertec.house.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import ru.clevertec.house.constant.Type;
import ru.clevertec.house.model.entity.parent.BaseModel;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class HouseHistory extends BaseModel {

    private Long houseId;
    private Long personId;
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private Type type;
}
