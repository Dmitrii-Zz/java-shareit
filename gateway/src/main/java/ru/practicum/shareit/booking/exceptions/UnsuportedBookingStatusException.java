package ru.practicum.shareit.booking.exceptions;

public class UnsuportedBookingStatusException extends RuntimeException {
    public UnsuportedBookingStatusException(final String mess) {
        super(mess);
    }
}
