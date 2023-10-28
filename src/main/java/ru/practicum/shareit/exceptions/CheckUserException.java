package ru.practicum.shareit.exceptions;

public class CheckUserException extends RuntimeException {
    public CheckUserException(final String mess) {
        super(mess);
    }
}
