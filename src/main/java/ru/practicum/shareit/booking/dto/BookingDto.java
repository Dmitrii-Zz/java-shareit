package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingDto {

    private long id;

    @NotNull(message = "Отсутствует ИД вещи в запросе.")
    private long itemId;

    @NotNull(message = "Отсутствует начальная точка отсчета.")
    @FutureOrPresent
    private LocalDateTime start;

    @NotNull(message = "Отсутствует конечная точка отсчета.")
    @FutureOrPresent
    private LocalDateTime end;

    private UserDto booker;

    private ItemDto item;

    private BookingStatus status;
}
