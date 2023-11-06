package ru.practicum.shareit.user.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class User {
    private long id;

    @NotBlank(message = "Имя не должно быть пустым.")
    private String name;

    @NotBlank(message = "Отсутствует email.")
    @Email(message = "Введен некорректный адрес электронной почты.")
    private String email;
}
