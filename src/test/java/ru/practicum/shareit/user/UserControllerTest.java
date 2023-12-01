package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserController userController;

    @Test
    public void getAllUsersTest() {
        List<UserDto> expectedUsers = List.of(UserDto.builder().build());
        when(userService.getAllUsers()).thenReturn(expectedUsers);
        List<UserDto> response = userController.getAllUsers();

        assertEquals("test", 1, response.size());
    }

    @Test
    public void createUserTest_whenWithoutName_thenReturnExceptionTest() {
        UserDto userDto = UserDto.builder()
                .email("user@yandex.ru")
                .build();


    }
}
