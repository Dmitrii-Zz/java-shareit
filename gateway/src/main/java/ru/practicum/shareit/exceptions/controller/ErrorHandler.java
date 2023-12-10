package ru.practicum.shareit.exceptions.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exceptions.model.ErrorResponse;
import ru.practicum.shareit.exceptions.ex.UnsuportedBookingStatusException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnsupportedBookingStatusException(final UnsuportedBookingStatusException e) {
        return new ErrorResponse(e.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleSpringValidationException(final ConstraintViolationException e) {
        return new ErrorResponse(e.getMessage());
    }
}