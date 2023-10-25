package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

public class UserMapper {
    public UserDto getUserDto(User user) {
        return new UserDto(user.getName());
    }
}
