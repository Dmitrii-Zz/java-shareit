package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.ex.*;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingStorage;
    private final ItemService itemService;
    private final UserService userService;

    public BookingDto createBooking(BookingDto bookingDto, long userId) {
        boolean isAvailableItem = itemService.checkIsAvailableItem(bookingDto.getItemId());

        if (!isAvailableItem) {
            throw new CheckAvailableItemException(
                    String.format("Вещь с id %d недоступна для аренды", bookingDto.getId()));
        }

        boolean isExistsUser = userService.checkExistsUser(userId);

        if (!isExistsUser) {
            throw new UserNotFoundException(String.format("Пользователь с id = %d не существует", userId));
        }

        if (bookingDto.getStart().isAfter(bookingDto.getEnd()) || bookingDto.getStart().equals(bookingDto.getEnd())) {
            throw new CheckStartAndEndBookingException("Неверны даты начала и окончания аренды.");
        }

        bookingDto.setBooker(userService.getUserById((userId)));
        bookingDto.setItem(itemService.getItemById(bookingDto.getItemId()));
        bookingDto.setStatus(BookingStatus.WAITING);
        Booking booking = BookingMapper.toBooking(bookingDto);
        return BookingMapper.toBookingDto(bookingStorage.save(booking));
    }

    public BookingDto addStatusBooking(long userId, long bookingId, boolean approved) {
        Optional<Booking> optionalBooking = bookingStorage.findById(bookingId);

        if (optionalBooking.isEmpty()) {
            throw new BookingNotFoundException(String.format("Аренда с id = %d не найдена.", bookingId));
        }

        Booking booking = optionalBooking.get();

        if (booking.getItem().getOwner().getId() != userId) {
            throw new NoAccessItemException(String.format("Вещь с id = %d недоступна для аренды.", booking.getId()));
        }

        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }

        return BookingMapper.toBookingDto(bookingStorage.save(booking));
    }
}
