package ru.practicum.shareit.request.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {
    private long id;

    private String description;

    private User requestor;

    private LocalDateTime created;
}
