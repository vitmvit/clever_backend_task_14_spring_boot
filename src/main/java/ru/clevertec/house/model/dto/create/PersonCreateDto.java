package ru.clevertec.house.model.dto.create;

import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.clevertec.house.constant.Sex;
import ru.clevertec.house.model.entity.Passport;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonCreateDto {

    @NotBlank(message = "Name is blank.")
    @NotEmpty(message = "Name is empty.")
    @Length(max = 15, message = "Name is long.")
    private String name;

    @NotBlank(message = "Surname is blank.")
    @NotEmpty(message = "Surname is empty.")
    @Length(max = 15, message = "Surname is long.")
    private String surname;

    private Sex sex;

    @Embedded
    private Passport passport;

    private UUID homeUuid;
}
