package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ex.CheckUserException;
import ru.practicum.shareit.exceptions.ex.ItemNotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentsRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemStorage;
    private final UserService userService;
    private final CommentsRepository commentStorage;

    public ItemDto createItem(ItemDto itemDto, long userId) {
        Item item = ItemMapper.toItem(itemDto);
        User owner = UserMapper.toUser(userService.getUserById(userId));
        item.setOwner(owner);
        return ItemMapper.toItemDto(itemStorage.save(item));
    }

    public ItemDto updateItem(ItemDto itemDto, long userId, long itemId) {
        Item itemFromBd = itemStorage.getReferenceById(itemId);
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

        return ItemMapper.toItemDto(itemStorage.save(itemFromBd));
    }

    public ItemDto getItemById(long id) {
        if (itemStorage.findById(id).isEmpty()) {
            throw new ItemNotFoundException("Отсутствует вещь с id = " + id);
        }

        return ItemMapper.toItemDto(itemStorage.getReferenceById(id));
    }

    public List<ItemDto> findAllUsersItems(long userId) {
        userService.checkExistsUser(userId);
        return itemStorage.findAll()
                .stream()
                .filter(item -> item.getOwner().getId() == userId)
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public List<ItemDto> searchItem(String text) {

        if (text.isBlank()) {
            return new ArrayList<>();
        }

        String searchText = text.toLowerCase().trim();
        return itemStorage.findAll()
                .stream()
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(searchText)
                             || item.getDescription().toLowerCase().contains(searchText))
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public boolean checkIsAvailableItem(long itemId) {
        return getItemById(itemId).getAvailable();
    }

    public Item getItem(long id) {
        return itemStorage.getReferenceById(id);
    }

    public CommentDto addComment(CommentDto commentDto, long itemId, long userId) {
        Comment comment = CommentMapper.toComment(commentDto);
        comment.setItem(ItemMapper.toItem(getItemById(itemId)));
        comment.setAuthor(UserMapper.toUser(userService.getUserById(userId)));
        comment.setCreated(LocalDateTime.now());
        return CommentMapper.toCommentDto(commentStorage.save(comment));
    }
}
