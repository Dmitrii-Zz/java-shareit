package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

public interface ItemRepository {

    Item createItem(Item item);

    Item getItemById(long id);

    Item update(Item item);

}
