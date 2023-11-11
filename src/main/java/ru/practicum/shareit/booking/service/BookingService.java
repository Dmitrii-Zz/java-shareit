package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.ex.CheckAvailableItemException;
import ru.practicum.shareit.exceptions.ex.CheckStartAndEndBookingException;
import ru.practicum.shareit.exceptions.ex.UserNotFoundException;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

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

}
