package ru.clevertec.house.model.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Passport {

    private String passportSeries;
    private String passportNumber;
}
