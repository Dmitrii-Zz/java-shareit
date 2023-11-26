package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ItemRequestDto {

    private long id;

    @NotBlank(message = "В запросе отсутствует описание запроса")
    private String description;

    private LocalDateTime created;

    private List<ItemDto> items;
}
