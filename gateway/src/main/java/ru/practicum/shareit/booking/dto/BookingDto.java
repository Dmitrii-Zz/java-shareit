package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    @Min(1)
    private long itemId;

    @NotNull(message = "Отсутствует начальная точка отсчета.")
    @FutureOrPresent
    private LocalDateTime start;

    @NotNull(message = "Отсутствует конечная точка отсчета.")
    @FutureOrPresent
    private LocalDateTime end;
}
