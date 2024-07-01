package ru.clevertec.house.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
public class PassportDto {

    @NotBlank(message = "Passport series is blank.")
    @NotEmpty(message = "Passport series is empty.")
    @Length(min = 2, max = 2, message = "Passport series is long.")
    private String passportSeries;

    @NotBlank(message = "Passport number is blank.")
    @NotEmpty(message = "Passport number is empty.")
    @Length(min = 7, max = 7, message = "Passport number is long.")
    private String passportNumber;
}
