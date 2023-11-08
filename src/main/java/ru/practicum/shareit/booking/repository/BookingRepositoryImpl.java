package ru.practicum.shareit.booking.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

@Repository
public class BookingRepositoryImpl implements BookingRepository {

    @Override
    public Booking createBooking(Booking booking) {
        return null;
    }

    @Override
    public Booking addStatusBooking(Booking booking) {
        return null;
    }

    @Override
    public Booking getBookingById(long bookingId) {
        return null;
    }

    @Override
    public List<Booking> findAllBookingByUserId(String state, long userId) {
        return null;
    }

    @Override
    public List<Booking> findAllBookingByOwnerId(String state, long userId) {
        return null;
    }
}
