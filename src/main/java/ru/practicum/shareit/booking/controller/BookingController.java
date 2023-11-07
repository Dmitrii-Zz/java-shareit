package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    @PostMapping
    public BookingDto createBooking(@Validated @RequestBody BookingDto bookingDto) {
        log.info("Запрос на создание аренды вещи {}.", bookingDto.getItem().getId());
        return null;
    }

    @PatchMapping("/{bookingId}")
    public BookingDto addStatusBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                       @RequestParam boolean approved,
                                       @PathVariable long bookingId) {
        log.info("Установить статус {} для запроса {} от юзера {}.", approved, bookingId, userId);
        return null;
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@RequestHeader("X-Sharer-User-Id") long userId,
                                     @PathVariable long bookingId) {
        log.info("Запрос информации об аренде {} от юзера {}.", bookingId, userId);
        return null;
    }

    @GetMapping
    public List<BookingDto> findAllBookingByUserId(@RequestHeader("X-Sharer-User-Id") long userId,
                                                   @RequestParam(defaultValue = "ALL") String state) {
        log.info("Вернуть список аренды {} юзера {}", state, userId);
        return null;
    }

    @GetMapping("/owner")
    public List<BookingDto> findAllBookingByOwnerId(@RequestHeader("X-Sharer-User-Id") long userId,
                                                   @RequestParam(defaultValue = "ALL") String state) {
        log.info("Вернуть список аренды {} юзера {}", state, userId);
        return null;
    }
}
