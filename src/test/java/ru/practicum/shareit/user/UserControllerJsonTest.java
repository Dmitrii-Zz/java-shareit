package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.user.dto.UserDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserControllerJsonTest {
    @Autowired
    private JacksonTester<UserDto> jacksonTester;

    @Test
    public void createUser_whenNameNotValid_test() throws IOException {
        UserDto userDto = UserDto.builder()
                .email("test@mail.ru")
                .name(" ")
                .build();

        JsonContent<UserDto> json = jacksonTester.write(userDto);

        assertThat(json).extractingJsonPathStringValue("$.name").isEqualTo(" ");
    }
}
