package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private long id = 1;

    @Override
    public Item createItem(Item item) {
        item.setId(id);
        items.put(id++, item);
        return item;
    }
}
