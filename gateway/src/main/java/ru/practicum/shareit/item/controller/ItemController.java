package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.client.ItemClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwnerDto;

import javax.validation.Valid;

import java.util.List;

import static ru.practicum.shareit.constant.Constants.USER_HEADER_ID;

@RestController
@RequestMapping(path = "/items")
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final ItemClient itemClient;
    @PostMapping
    public ItemDto createItem(@RequestHeader(USER_HEADER_ID) long userId,
                              @Valid @RequestBody ItemDto itemDto) {
        log.info("Запрос создания вещи юзера id = " + userId);
        return null; //itemService.createItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader(USER_HEADER_ID) long userId,
                              @PathVariable long itemId,
                              @RequestBody ItemDto itemDto) {
        log.info("Обновление вещи пользователя id = " + userId);
        return null; //itemService.updateItem(itemDto, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@RequestHeader(USER_HEADER_ID) long userId,
                               @PathVariable long itemId) {
        log.info("Запрос вещи id = " + itemId);
        return null; //itemService.getItemById(itemId, userId);
    }

    @GetMapping
    public List<ItemOwnerDto> getAllItemOwner(@RequestHeader(USER_HEADER_ID) long userId,
                                              @RequestParam(defaultValue = "0") int from,
                                              @RequestParam(defaultValue = "10") int size) {
        log.info("Запрос всех вещей юзера id = {}, from = {}, size = {}", userId, from, size);
        return null; // itemService.findAllOwnersItems(userId, from, size);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItem(@RequestParam String text,
                                    @RequestParam(defaultValue = "0") int from,
                                    @RequestParam(defaultValue = "10") int size) {
        log.info("Поиск вещи по тексту = {}, from = {}, size = {}.", text, from, size);
        return null; //itemService.searchItem(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader(USER_HEADER_ID) long userId,
                                 @PathVariable long itemId,
                                 @RequestBody @Valid CommentDto commentDto) {
        return null; //itemService.addComment(commentDto, itemId, userId);
    }
}
