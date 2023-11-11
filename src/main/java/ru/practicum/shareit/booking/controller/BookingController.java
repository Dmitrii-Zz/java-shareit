package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoWithoutDate;
import ru.practicum.shareit.booking.service.BookingService;
import static ru.practicum.shareit.constatnt.Constants.USER_HEADER_ID;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDto createBooking(@Validated @RequestBody BookingDto bookingDto,
                                    @RequestHeader(USER_HEADER_ID) long userId) {
        log.info("Запрос на создание аренды вещи {} от юзера {}.", bookingDto.getItemId(), userId);
        return bookingService.createBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoWithoutDate addStatusBooking(@RequestHeader(USER_HEADER_ID) long userId,
                                                  @RequestParam boolean approved,
                                                  @PathVariable long bookingId) {
        log.info("Установить статус {} для запроса {} от юзера {}.", approved, bookingId, userId);
        return bookingService.addStatusBooking(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@RequestHeader(USER_HEADER_ID) long userId,
                                     @PathVariable long bookingId) {
        log.info("Запрос информации об аренде {} от юзера {}.", bookingId, userId);
        return null;
    }

    @GetMapping
    public List<BookingDto> findAllBookingByUserId(@RequestHeader(USER_HEADER_ID) long userId,
                                                   @RequestParam(defaultValue = "ALL") String state) {
        log.info("Вернуть список аренды {} юзера {}", state, userId);
        return null;
    }

    @GetMapping("/owner")
    public List<BookingDto> findAllBookingByOwnerId(@RequestHeader(USER_HEADER_ID) long userId,
                                                   @RequestParam(defaultValue = "ALL") String state) {
        log.info("Вернуть список аренды {} юзера {}", state, userId);
        return null;
    }
}
