package ru.practicum.shareit.exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(final String mess) {
        super(mess);
    }
}