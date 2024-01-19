package ru.clevertec.house.model.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.clevertec.house.constant.Sex;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonCreateDto {

    @NotBlank(message = "Name is blank.")
    @NotEmpty(message = "Name is empty.")
    @Size(max = 15, message = "Name is long.")
    private String name;

    @NotBlank(message = "Surname is blank.")
    @NotEmpty(message = "Surname is empty.")
    @Size(max = 15, message = "Surname is long.")
    private String surname;

    private Sex sex;

    @NotBlank(message = "Passport series is blank.")
    @NotEmpty(message = "Passport series is empty.")
    @Size(max = 15, message = "Passport series is long.")
    private String passportSeries;

    @NotBlank(message = "Passport number is blank.")
    @NotEmpty(message = "Passport number is empty.")
    @Size(max = 15, message = "Passport number is long.")
    private String passportNumber;
}
