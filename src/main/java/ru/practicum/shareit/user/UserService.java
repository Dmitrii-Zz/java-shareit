package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.EmailDuplicateException;

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

    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    public User updateUser(User user, long userId) {
        validDublicateEmail(user);
        user.setId(userId);
        return repository.updateUser(user);
    }

    public void deleteUser(long id) {
        repository.deleteUser(id);
    }

    private void validDublicateEmail(User user) {
        boolean isEmailAvailable = repository.getAllUsers()
                .stream()
                .filter(u -> u.getId() != user.getId())
                .noneMatch(u -> u.getEmail().equals(user.getEmail()));

        log.info("isEmailAvailable = " + isEmailAvailable);
        if (!isEmailAvailable) {
            throw new EmailDuplicateException("Такой email существует.");
        }
    }
}
