package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
           "where item_id = ?1")
    List<Comment> getCommentsByItemId(long itemId);
}
