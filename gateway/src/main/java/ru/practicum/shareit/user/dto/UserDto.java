package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.shareit.valid.Mark.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;

    @NotBlank(groups = {Create.class}, message = "Имя не должно быть пустым.")
    @Length(max = 255, message = "Длина имени не должна превышать 255 симоволов.")
    private String name;

    @NotBlank(groups = {Create.class}, message = "Отсутствует email.")
    @Email(groups = {Create.class, Update.class}, message = "Введен некорректный адрес электронной почты.")
    private String email;
}
