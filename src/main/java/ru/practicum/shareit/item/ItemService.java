package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemStorage;
    private final UserService userService;

    public Item createItem(Item item, long userId) {
        item.setOwner(userService.getUserById(userId));
        return itemStorage.createItem(item);
    }
}
