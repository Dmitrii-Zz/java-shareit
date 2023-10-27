package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.EmailDuplicateException;
import ru.practicum.shareit.exceptions.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository repository;

    public User createUser(User user) {
        validDublicateEmail(user);
        return repository.createUser(user);
    }

    public User getUserById(long id) {
        if (!repository.findUserByID(id)) {
            throw new UserNotFoundException("Пользователь с id = " + id + " не найден");
        }

        return repository.getUserById(id);
    }

    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    public User updateUser(User user, long userId) {
        user.setId(userId);
        validDublicateEmail(user);
        User updateUser = repository.getUserById(userId);

        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            updateUser.setEmail(user.getEmail());
        }

        if (user.getName() != null && !user.getName().isBlank()) {
            updateUser.setName(user.getName());
        }

        return repository.updateUser(updateUser);
    }

    public void deleteUser(long id) {
        repository.deleteUser(id);
    }

    private void validDublicateEmail(User user) {
        boolean isEmailAvailable = repository.getAllUsers()
                .stream()
                .filter(u -> u.getId() != user.getId())
                .noneMatch(u -> u.getEmail().equals(user.getEmail()));

        if (!isEmailAvailable) {
            throw new EmailDuplicateException("email \"" + user.getEmail() + "\" занят.");
        }
    }
}
