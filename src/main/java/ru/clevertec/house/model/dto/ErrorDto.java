package ru.clevertec.house.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorDto {

    private final String errorMessage;
    private final Integer errorCode;

    public ErrorDto(HttpStatus httpStatus) {
        errorMessage = httpStatus.getReasonPhrase();
        errorCode = httpStatus.value();
    }

    public ErrorDto(String errorMessage, Integer errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
