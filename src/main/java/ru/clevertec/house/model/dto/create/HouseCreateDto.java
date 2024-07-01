package ru.clevertec.house.model.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HouseCreateDto {

    @NotBlank(message = "Area is blank.")
    @NotEmpty(message = "Area is empty.")
    @Length(max = 15, message = "Area is long.")
    private String area;

    @NotBlank(message = "Country is blank.")
    @NotEmpty(message = "Country is empty.")
    @Length(max = 15, message = "Country is long.")
    private String country;

    @NotBlank(message = "City is blank.")
    @NotEmpty(message = "City is empty.")
    @Length(max = 15, message = "City is long.")
    private String city;

    @NotBlank(message = "Street is blank.")
    @NotEmpty(message = "Street is empty.")
    @Length(max = 15, message = "Street is long.")
    private String street;

    @NotBlank(message = "Number is blank.")
    @NotEmpty(message = "Number is empty.")
    @Length(max = 15, message = "Number is long.")
    private int number;
}
