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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        userService.checkExistsUser(userId);

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
        Booking booking = findBookingById(bookingId);

        if (booking.getItem().getOwner().getId() != userId) {
            throw new NoAccessItemException(
                    String.format("Вещь с id = %d недоступна для юзера id = %d.", booking.getId(), userId));
        }

        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }

        return BookingMapper.toBookingDto(bookingStorage.save(booking));
    }

    public BookingDto getBookingById(long userId, long bookingId) {
        Booking booking = findBookingById(bookingId);
        userService.checkExistsUser(userId);

        if (booking.getBooker().getId() == userId || booking.getItem().getOwner().getId() == userId) {
            return BookingMapper.toBookingDto(bookingStorage.getReferenceById(bookingId));
        }

        throw new NoAccessItemException(
                String.format("Вещь с id = %d недоступна для юзера id = %d.", booking.getItem().getId(), userId));
    }

    public List<BookingDto> findAllBookingByUserId(long userId, String state) {
        userService.checkExistsUser(userId);

        if (state.equals("ALL")) {
            return bookingStorage.findAll().stream()
                    .filter(x -> x.getBooker().getId() == userId)
                    .map(BookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        try {
            return bookingStorage.findAll().stream()
                    .filter(x -> x.getBooker().getId() == userId)
                    .filter(x -> x.getStatus().equals(BookingStatus.valueOf(state)))
                    .map(BookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new UnsuportedBookingStatusException("Unknown state: " + state);
        }
    }

    public List<BookingDto> findAllBookingByOwnerId (long userId, String state) {
        userService.checkExistsUser(userId);

        if (state.equals("ALL")) {
            return bookingStorage.findAll().stream()
                    .filter(x -> x.getItem().getOwner().getId() == userId)
                    .map(BookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        }
        try {
            return bookingStorage.findAll().stream()
                    .filter(x -> x.getItem().getOwner().getId() == userId)
                    .filter(x -> x.getStatus().equals(BookingStatus.valueOf(state)))
                    .map(BookingMapper::toBookingDto)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new UnsuportedBookingStatusException("Unknown state: " + state);
        }
    }

    private Booking findBookingById(long bookingId) {
        Optional<Booking> optionalBooking = bookingStorage.findById(bookingId);

        if (optionalBooking.isEmpty()) {
            throw new BookingNotFoundException(String.format("Аренда с id = %d не найдена.", bookingId));
        }

        return optionalBooking.get();
    }
}
