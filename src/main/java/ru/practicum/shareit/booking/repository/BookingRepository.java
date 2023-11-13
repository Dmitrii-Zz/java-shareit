package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b " +
           "where user_id = ?1 ")
    List<Booking> getAllBookingByUSerId(long userId);

    @Query("select b from Booking b " +
            "where user_id = ?1 " +
            "and b.end < localtimestamp")
    List<Booking> getPastBookingByUserId(long userId);

    @Query("select b from Booking b " +
            "where user_id = ?1 " +
            "and start > localtimestamp")
    List<Booking> getFutureBookingByUserId(long userId);

    @Query("select b from Booking b " +
            "where user_id = ?1 " +
            "and b.status = ?2")
    List<Booking> getBookingWithStatusByUserId(long userId, BookingStatus state);

    @Query("select b from Booking b " +
           "inner join b.item i " +
           "where i.owner.id = ?1")
    List<Booking> getAllBookingByOwnerId(long userId);

    @Query("select b from Booking b " +
            "inner join b.item i " +
            "where i.owner.id = ?1 " +
            "and b.end < localtimestamp")
    List<Booking> getPastBookingByOwnerId(long userId);

    @Query("select b from Booking b " +
            "inner join b.item i " +
            "where i.owner.id = ?1 " +
            "and b.start > localtimestamp")
    List<Booking> getFutureBookingByOwnerId(long userId);

    @Query("select b from Booking b " +
            "inner join b.item i " +
            "where i.owner.id = ?1 " +
            "and b.status = ?2")
    List<Booking> getBookingWithStatusByOwnerId(long userId, BookingStatus state);
}
