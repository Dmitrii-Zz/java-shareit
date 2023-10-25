package ru.practicum.shareit.exceptions;

public class EmailDuplicateException extends RuntimeException {
    public EmailDuplicateException(final String msg) {
        super(msg);
    }
}
