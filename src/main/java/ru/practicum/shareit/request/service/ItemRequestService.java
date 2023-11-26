package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemRequestService {
    private final ItemRequestRepository itemRequestStorage;
    private final UserService userService;

    public ItemRequestDto addRequest(ItemRequestDto itemRequestDto, long userId) {
        userService.checkExistsUser(userId);
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto);
        itemRequest.setRequestor(UserMapper.toUser(userService.getUserById(userId)));
        itemRequest.setCreated(LocalDateTime.now());
        return ItemRequestMapper.toItemRequestDto(itemRequestStorage.save(itemRequest));
    }

    public List<ItemRequestDto> getAllItemRequest(long userId) {
        userService.checkExistsUser(userId);
        return itemRequestStorage.findAllByRequestorId(userId).stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .collect(Collectors.toList());
    }

    public List<ItemRequestDto> getAllOtherUsersItemRequest(long userId, int from, int size) {
        userService.checkExistsUser(userId);
        log.info("From = " + from + " Size = " + size);
        return itemRequestStorage.findAllByRequestorIdIsNotOrderByCreated(userId, PageRequest.of(from, size))
                .stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .collect(Collectors.toList());
    }

    public ItemRequestDto getItemRequest(long requestId) {
        return null;
    }
}
