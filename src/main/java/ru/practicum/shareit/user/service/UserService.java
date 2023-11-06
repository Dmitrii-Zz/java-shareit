package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.EmailDuplicateException;
import ru.practicum.shareit.exceptions.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public UserDto createUser(UserDto userDto) {
        validDublicateEmail(userDto);
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(repository.createUser(user));
    }

    public UserDto getUserById(long id) {
        if (!checkExistsUser(id)) {
            throw new UserNotFoundException("Пользователь с id = " + id + " не найден");
        }

        return UserMapper.toUserDto(repository.getUserById(id));
    }

    public List<UserDto> getAllUsers() {
        return repository.getAllUsers()
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(UserDto userDto, long userId) {
        userDto.setId(userId);
        validDublicateEmail(userDto);
        User updateUser = repository.getUserById(userId);

        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            updateUser.setEmail(userDto.getEmail());
        }

        if (userDto.getName() != null && !userDto.getName().isBlank()) {
            updateUser.setName(userDto.getName());
        }

        return UserMapper.toUserDto(repository.updateUser(updateUser));
    }

    public void deleteUser(long id) {
        repository.deleteUser(id);
    }

    public boolean checkExistsUser(long id) {
        return repository.findUserByID(id);
    }

    private void validDublicateEmail(UserDto user) {
        boolean isEmailAvailable = repository.getAllUsers()
                .stream()
                .filter(u -> u.getId() != user.getId())
                .noneMatch(u -> u.getEmail().equals(user.getEmail()));

        if (!isEmailAvailable) {
            throw new EmailDuplicateException("email \"" + user.getEmail() + "\" занят.");
        }
    }
}
