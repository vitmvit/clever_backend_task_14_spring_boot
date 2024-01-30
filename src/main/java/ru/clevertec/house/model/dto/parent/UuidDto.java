package ru.clevertec.house.model.dto.parent;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.util.UUID;

@Getter
@Setter
@FieldNameConstants
public class UuidDto {

    private UUID uuid;
}
