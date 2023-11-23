package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    private long id;

    @NotBlank(message = "В запросе отсутствует текст отзыва.")
    @Length(max = 2000, message = "Превышена длина отзыва.")
    private String text;

    private String authorName;

    private LocalDateTime created;
}
