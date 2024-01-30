package ru.clevertec.house.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.house.exception.*;
import ru.clevertec.house.model.dto.ErrorDto;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(InvalidJwtException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public ErrorDto error(InvalidJwtException e) {
        return new ErrorDto(e.getMessage(), HttpStatus.FOUND.value());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto error(EntityNotFoundException e) {
        return new ErrorDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(PatchException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto error(PatchException e) {
        return new ErrorDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(EmptyListException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto error(EmptyListException e) {
        return new ErrorDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(CacheNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto error(CacheNotFoundException e) {
        return new ErrorDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto error(RuntimeException e) {
        return new ErrorDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }
}
