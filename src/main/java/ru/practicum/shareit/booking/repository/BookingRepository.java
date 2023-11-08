package ru.practicum.shareit.booking.repository;

import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingRepository {

    Booking createBooking(Booking booking);

    Booking addStatusBooking(Booking booking);

    Booking getBookingById(long bookingId);

    List<Booking> findAllBookingByUserId(String state, long userId);

    List<Booking> findAllBookingByOwnerId(String state, long userId);
}
