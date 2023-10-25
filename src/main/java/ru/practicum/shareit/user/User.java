package ru.practicum.shareit.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */

@Data
public class User {
    private long id;

    @NotNull(message = "Отсутствует имя юзера.")
    @NotBlank(message = "Имя не должно быть пустым.")
    private String name;

    @NotNull(message = "Отсутствует email.")
    @Email(message = "Введен некорректный адрес электронной почты.")
    private String email;
}
