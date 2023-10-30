package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;

    @NotBlank(message = "Имя не должно быть пустым.")
    private String name;

    @NotBlank(message = "Отсутствует email.")
    @Email(message = "Введен некорректный адрес электронной почты.")
    private String email;

    public UserDto(String name) {
        this.name = name;
    }
}
