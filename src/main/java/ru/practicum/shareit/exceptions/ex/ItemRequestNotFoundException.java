package ru.practicum.shareit.exceptions.ex;

public class ItemRequestNotFound extends RuntimeException {
    public ItemRequestNotFound(final String mess) {
        super(mess);
    }
}
