package ru.practicum.shareit.user.dto;

import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String name;
    private String email;

    public UserDto(String name) {
        this.name = name;
    }
}
