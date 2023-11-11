package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class BookingDtoWithoutDate {

    private long id;

    private long itemId;

    private UserDto booker;

    private ItemDto item;

    private BookingStatus status;

    private LocalDateTime start;

    private LocalDateTime end;
}
