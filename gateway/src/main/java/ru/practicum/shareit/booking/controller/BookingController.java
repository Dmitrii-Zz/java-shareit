package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.client.BookingClient;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingStatus;
import ru.practicum.shareit.booking.exceptions.UnsuportedBookingStatusException;

import javax.validation.constraints.Min;
import java.util.List;

import static ru.practicum.shareit.constant.Constants.USER_HEADER_ID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> createBooking(@Validated @RequestBody BookingDto bookingDto,
                                                @RequestHeader(USER_HEADER_ID) @Min(1) long userId) {
        log.info("Запрос на создание аренды вещи {} от юзера {}.", bookingDto.getItemId(), userId);
        return bookingClient.createBooking(bookingDto, userId); //bookingService.createBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto addStatusBooking(@RequestHeader(USER_HEADER_ID) @Min(1) long userId,
                                       @RequestParam boolean approved,
                                       @PathVariable @Min(1) long bookingId) {
        log.info("Установить статус {} для запроса {} от юзера {}.", approved, bookingId, userId);
        return null; //bookingService.addStatusBooking(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@RequestHeader(USER_HEADER_ID) @Min(1) long userId,
                                     @PathVariable @Min(1) long bookingId) {
        log.info("Запрос информации об аренде {} от юзера {}.", bookingId, userId);
        return null; //bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping
    public List<BookingDto> findAllBookingByUserId(@RequestHeader(USER_HEADER_ID) @Min(1) long userId,
                                                   @RequestParam(defaultValue = "ALL") String state,
                                                   @RequestParam(defaultValue = "0") @Min(1) int from,
                                                   @RequestParam(defaultValue = "10") @Min(1) int size) {
        BookingStatus status = BookingStatus.from(state)
                .orElseThrow(() -> new UnsuportedBookingStatusException("Unknown state: " + state));
        log.info("Вернуть список аренды {} юзера {} c параметрами from {} и size {}", state, userId, from, size);
        return null; //bookingService.findAllBookingByUserId(userId, state, from, size);
    }

    @GetMapping("/owner")
    public List<BookingDto> findAllBookingByOwnerId(@RequestHeader(USER_HEADER_ID) long userId,
                                                    @RequestParam(defaultValue = "ALL") String state,
                                                    @RequestParam(defaultValue = "0") @Min(1) int from,
                                                    @RequestParam(defaultValue = "10") @Min(1) int size) {
        log.info("Вернуть список аренды {} юзера {}", state, userId);
        return null; //bookingService.findAllBookingByOwnerId(userId, state, from, size);
    }
}