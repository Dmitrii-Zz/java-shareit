package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.Item;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public Item createItem(@RequestHeader("X-Sharer-User-Id") long userId, @RequestBody Item item) {
        return itemService.createItem(item, userId);
    }
}
