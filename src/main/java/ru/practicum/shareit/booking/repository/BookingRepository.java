package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByBookerId(long userId);

    List<Booking> findByBookerIdAndEndBefore(long userId, LocalDateTime dateTime);

    List<Booking> findAllByBookerIdAndStartAfter(long userId, LocalDateTime dateTime);

    List<Booking> findByBookerIdAndStatus(long userId, BookingStatus state);

    @Query("select b from Booking b " +
            "where user_id = ?1 " +
            "and (b.status = ?2 or b.status = ?3 or b.status = ?4)" +
            "and (b.end > localtimestamp and b.start < localtimestamp)")
    List<Booking> getBookingCurrentByUserId(
            long userId, BookingStatus waiting, BookingStatus rejected, BookingStatus current);

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

    @Query("select b from Booking b " +
           "inner join b.item i " +
           "where i.owner.id = ?1 " +
           "and (b.status = ?2 or b.status = ?3 or b.status = ?4)" +
           "and (b.end > localtimestamp and b.start < localtimestamp)")
    List<Booking> getBookingCurrentByOwnerId(
            long userId, BookingStatus waiting, BookingStatus rejected, BookingStatus current);

    @Query("select b from Booking b " +
           "inner join b.item i " +
           "where i.id = ?1 " +
           "and b.item.id = ?1 " +
           "and b.booker.id = ?2 " +
           "and b.end < localtimestamp")
    Optional<List<Booking>> getBookingItemWhichTookUser(long itemId, long userId);

    List<Booking> findByItemId(long itemId);

}
