package ru.practicum.shareit.exceptions;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(final String mess) {
        super(mess);
    }
}
