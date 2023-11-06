package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class Item {

    private long id;

    @NotBlank(message = "В запросе отсутствует имя вещи.")
    private String name;

    @NotBlank(message = "В запросе отсутствует описание вещи.")
    private String description;

    @NotNull(message = "В запросе отсутствует статус запроса к аренде.")
    private Boolean available;

    private User owner;

    private ItemRequest request;
}
