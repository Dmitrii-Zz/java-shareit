package ru.practicum.shareit.user;

import java.util.List;

public interface UserRepository {

    User createUser(User user);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(long id);

}