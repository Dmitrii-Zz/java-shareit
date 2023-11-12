package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.shareit.constatnt.Constants.USER_HEADER_ID;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto createItem(@RequestHeader(USER_HEADER_ID) long userId,
                              @Valid @RequestBody ItemDto itemDto) {
        log.info("Запрос создания вещи юзера id = " + userId);
        return itemService.createItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader(USER_HEADER_ID) long userId,
                           @PathVariable long itemId,
                           @RequestBody ItemDto itemDto) {
        log.info("Обновление вещи пользователя id = " + userId);
        return itemService.updateItem(itemDto, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable long itemId) {
        log.info("Запрос вещи id = " + itemId);
        return itemService.getItemById(itemId);
    }

    @GetMapping
    public List<ItemDto> getAllItemUser(@RequestHeader(USER_HEADER_ID) long userId) {
        log.info("Запрос всех вещей юзера id = " + userId);
        return itemService.findAllUsersItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItem(@RequestParam String text) {
        log.info("Поиск вещи по тексту = " + text);
        return itemService.searchItem(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader(USER_HEADER_ID) long userId,
                                 @PathVariable long itemId,
                                 @RequestBody @Valid CommentDto commentDto) {
        return itemService.addComment(commentDto, itemId, userId);
    }
}