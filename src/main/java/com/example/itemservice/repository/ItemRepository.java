package com.example.itemservice.repository;

import com.example.itemservice.domain.item.Item;
import com.example.itemservice.exception.item.NotFoundItemException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {
    private static final Map<Long, Item> store = new HashMap<>();

    private static final AtomicLong sequence = new AtomicLong(0);

    public Item save(Item item) {
        item.setId(sequence.incrementAndGet());
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return Optional.ofNullable(store.get(id))
            .orElseThrow(NotFoundItemException::new);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateItem) {
        Item item = findById(itemId);

        item.modifyBy(updateItem);
    }

    public void clear() {
        store.clear();
        sequence.set(0);
    }
}
