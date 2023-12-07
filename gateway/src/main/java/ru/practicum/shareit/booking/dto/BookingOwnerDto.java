package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingOwnerDto {

    private long id;

    private long bookerId;

    private LocalDateTime start;

    private LocalDateTime end;

    private BookingStatus status;
}
