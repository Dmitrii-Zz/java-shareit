package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
public class ItemRequestDto {

    private long id;

    @NotBlank(message = "В запросе отсутствует описание запроса")
    private String description;

    private LocalDateTime created;
}
