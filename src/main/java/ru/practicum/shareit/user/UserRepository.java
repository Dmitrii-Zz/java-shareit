package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {

    User createUser(User user);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(long id);

    User getUserById(long id);

    boolean findUserByID(long id);

}