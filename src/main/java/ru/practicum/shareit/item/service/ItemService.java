package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.CheckUserException;
import ru.practicum.shareit.exceptions.ItemNotFoundException;
import ru.practicum.shareit.exceptions.UserNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemStorage;
    private final UserService userService;
    private final ItemMapper itemMapper;
    private final UserMapper userMapper;

    public ItemDto createItem(ItemDto itemDto, long userId) {
        Item item = itemMapper.toItem(itemDto);
        User owner = userMapper.toUser(userService.getUserById(userId));
        item.setOwner(owner);
        return itemMapper.toItemDto(itemStorage.createItem(item));
    }

    public ItemDto updateItem(ItemDto itemDto, long userId, long itemId) {
        Item itemFromBd = itemStorage.getItemById(itemId);
        boolean checkUser = itemFromBd.getOwner().getId() == userId;

        if (!checkUser) {
            throw new CheckUserException("Отсутствует доступ к вещи id = " + itemId);
        }

        if (itemDto.getName() != null) {
            itemFromBd.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            itemFromBd.setDescription(itemDto.getDescription());
        }

        if (itemDto.getAvailable() != null) {
            itemFromBd.setAvailable(itemDto.getAvailable());
        }

        return itemMapper.toItemDto(itemStorage.update(itemFromBd));
    }

    public ItemDto getItemById(long id) {

        if (!itemStorage.findItemById(id)) {
            throw new ItemNotFoundException("Отсутствует вещь с id = " + id);
        }

        return itemMapper.toItemDto(itemStorage.getItemById(id));
    }

    public List<ItemDto> findAllUsersItems(long userId) {

        if (!userService.checkExistsUser(userId)) {
            throw new UserNotFoundException(String.format("Пользователь с id = %d не существует", userId));
        }

        return itemStorage.getAllItem()
                .stream()
                .filter(item -> item.getOwner().getId() == userId)
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public List<ItemDto> searchItem(String text) {

        if (text.isBlank()) {
            return new ArrayList<>();
        }

        String searchText = text.toLowerCase().trim();
        return itemStorage.getAllItem()
                .stream()
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(searchText)
                             || item.getDescription().toLowerCase().contains(searchText))
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
