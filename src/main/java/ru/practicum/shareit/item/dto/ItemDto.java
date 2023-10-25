package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.user.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    private long id;
    private String name;
    private String description;
    private boolean isAvailable;
    private User owner;
    private Long request;

    public ItemDto(String name, String description, boolean isAvailable, Long request) {
        this.name = name;
        this.description = description;
        this.isAvailable = isAvailable;
        this.request = request;
    }
}
