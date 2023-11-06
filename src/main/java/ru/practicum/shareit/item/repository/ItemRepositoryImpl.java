package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public Item getItemById(long id) {
        return items.get(id);
    }

    @Override
    public Item update(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public boolean findItemById(long id) {
        return items.containsKey(id);
    }

    @Override
    public List<Item> getAllItem() {
        return new ArrayList<>(items.values());
    }
}
