package ru.practicum.shareit.request;

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
@Entity
@Table(name = "requests")
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {
    @Column(name = "request_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User requestor;

    @Column(name = "created")
    private LocalDateTime created;
}
