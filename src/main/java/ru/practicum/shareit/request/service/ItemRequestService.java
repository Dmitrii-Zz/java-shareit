package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.repository.ItemRequestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRequestService {
    private final ItemRequestRepository itemRequestStorage;

    public ItemRequestDto addRequest(ItemRequestDto itemRequestDto) {
        return null;
    }

    public List<ItemRequestDto> getAllItemRequest(long userId) {
        return null;
    }

    public List<ItemRequestDto> getAllOtherUsersItemRequest() {
        return null;
    }

    public ItemRequestDto getItemRequest(long requestId) {
        return null;
    }
}
