package ru.practicum.shareit.request.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.request.model.ItemRequest;

import java.awt.print.Pageable;
import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> findAllByRequestorId(long userId);

    List<ItemRequest> findAllByRequestorIdIsNotOrderByCreated(long userId, PageRequest pageable);
}
