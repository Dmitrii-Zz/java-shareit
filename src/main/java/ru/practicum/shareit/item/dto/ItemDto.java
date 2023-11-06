package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ItemDto {

    private long id;

    @NotBlank(message = "В запросе отсутствует имя вещи.")
    private String name;

    @NotBlank(message = "В запросе отсутствует описание вещи.")
    private String description;

    @NotNull(message = "В запросе отсутствует статус запроса к аренде.")
    private Boolean available;

    public ItemDto(long id, String name, String description, Boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = isAvailable;
    }
}
