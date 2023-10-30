package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@Validated @RequestBody User user) {
        log.info("Запрос на создание юзера.");
        return userService.createUser(user);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable long userId) {
        log.info("Запрос пользователя id = " + userId);
        return userService.getUserById(userId);
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Запрос всех пользователей.");
        return userService.getAllUsers();
    }

    @PatchMapping("/{userId}")
    public User updateUser(@RequestBody User user, @PathVariable long userId) {
        log.info("Обновление юзера id = " + userId);
        return userService.updateUser(user, userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        log.info("Удаление юзера id = " + userId);
        userService.deleteUser(userId);
    }
}
