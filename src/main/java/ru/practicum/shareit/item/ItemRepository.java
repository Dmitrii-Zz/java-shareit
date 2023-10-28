package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {

    Item createItem(Item item);

    Item getItemById(long id);

    Item update(Item item);

    boolean findItemById(long id);

    List<Item> getAllItem();

}
