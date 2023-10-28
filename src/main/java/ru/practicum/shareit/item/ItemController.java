package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.Item;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public Item createItem(@RequestHeader("X-Sharer-User-Id") long userId,
                           @Valid @RequestBody Item item) {
        log.info("Запрос создания вещи юзера id = " + userId);
        return itemService.createItem(item, userId);
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@RequestHeader("X-Sharer-User-Id") long userId,
                           @PathVariable long itemId,
                           @RequestBody Item item) {
        log.info("Обновление вещи пользователя id = " + userId);
        return itemService.updateItem(item, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public Item getItemById(@PathVariable long itemId) {
        log.info("Запрос вещи id = " + itemId);
        return itemService.getItemById(itemId);
    }

    @GetMapping
    public List<Item> getAllItemUser(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Запрос всех вещей юзера id = " +userId);
        return itemService.findAllUsersItems(userId);
    }

    @GetMapping("/search")
    public List<Item> searchItem(@RequestParam String text) {
        log.info("Поиск вещи по тексту = " + text);
        return itemService.searchItem(text);
    }
}
