package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.ex.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
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
        Item item = itemService.getItem(bookingDto.getItemId());
        log.info("у вещи " + item.getId() + " пользователь - " + item.getOwner());

        if (item.getOwner() != null && item.getOwner().getId() == userId) {
            throw new CheckUserNotOwnerItemException("Нельзя взять свою вещь в аренду.");
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
        List<Booking> bookings;

        switch (state) {
            case "ALL":
                bookings = bookingStorage.getAllBookingByUSerId(userId);
                break;
            case "PAST":
                bookings = bookingStorage.getPastBookingByUserId(userId);
                break;
            case "FUTURE":
                bookings = bookingStorage.getFutureBookingByUserId(userId);
                break;
            default:
                bookings = bookingStorage.getBookingWithStatusByUserId(userId, konvertBookingStatus(state));
                break;
        }

        return getListBookingDto(bookings);
    }

    public List<BookingDto> findAllBookingByOwnerId(long userId, String state) {
        userService.checkExistsUser(userId);
        List<Booking> bookings;

        switch (state) {
            case "ALL":
                bookings = bookingStorage.getAllBookingByOwnerId(userId);
                break;
            case "PAST":
                bookings = bookingStorage.getPastBookingByOwnerId(userId);
                break;
            case "FUTURE":
                bookings = bookingStorage.getFutureBookingByOwnerId(userId);
                break;
            default:
                bookings = bookingStorage.getBookingWithStatusByOwnerId(userId, konvertBookingStatus(state));
                break;
        }

        return getListBookingDto(bookings);
    }

    private List<BookingDto> getListBookingDto(List<Booking> bookings) {
        return bookings.stream()
                .map(BookingMapper::toBookingDto)
                .sorted((x, x1) -> x1.getStart().compareTo(x.getStart()))
                .collect(Collectors.toList());
    }

    private Booking findBookingById(long bookingId) {
        Optional<Booking> optionalBooking = bookingStorage.findById(bookingId);

        if (optionalBooking.isEmpty()) {
            throw new BookingNotFoundException(String.format("Аренда с id = %d не найдена.", bookingId));
        }

        return optionalBooking.get();
    }

    private BookingStatus konvertBookingStatus(String state) {
        BookingStatus bookingStatus;

        switch (state) {
            case ("CURRENT"):
                bookingStatus = BookingStatus.APPROVED;
                break;
            case ("WAITING"):
                bookingStatus = BookingStatus.WAITING;
                break;
            case ("REJECTED"):
                bookingStatus = BookingStatus.REJECTED;
                break;
            default:
                throw new UnsuportedBookingStatusException("Unknown state: " + state);
        }

        return bookingStatus;
    }
}