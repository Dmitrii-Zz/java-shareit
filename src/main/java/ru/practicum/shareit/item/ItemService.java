package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.CheckUserException;
import ru.practicum.shareit.exceptions.ItemNotFoundException;
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

    public Item updateItem(Item item, long userId, long itemId) {
        Item itemFromBd = itemStorage.getItemById(itemId);
        boolean checkUser = itemFromBd.getOwner().getId() == userId;

        if (!checkUser) {
            throw new CheckUserException("Отсутствует доступ к вещи id = " + itemId);
        }

        if (item.getName() != null) {
            itemFromBd.setName(item.getName());
        }

        if (item.getDescription() != null) {
            itemFromBd.setDescription(item.getDescription());
        }

        if (item.getAvailable() != null) {
            itemFromBd.setAvailable(item.getAvailable());
        }

        return itemStorage.update(itemFromBd);
    }

    public Item getItemById(long id) {

        if (!itemStorage.findItemById(id)) {
            throw new ItemNotFoundException("Отсутствует вещь с id = " + id);
        }

        return itemStorage.getItemById(id);
    }
}
