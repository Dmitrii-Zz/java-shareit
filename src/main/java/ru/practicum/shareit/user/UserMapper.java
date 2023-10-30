package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public UserDto getUserDto(User user) {
        return new UserDto(user.getName());
    }
}
