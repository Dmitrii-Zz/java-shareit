package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserDto {
    private long id;

    @NotBlank(message = "Имя не должно быть пустым.")
    private String name;

    @NotBlank(message = "Отсутствует email.")
    @Email(message = "Введен некорректный адрес электронной почты.")
    private String email;
}
