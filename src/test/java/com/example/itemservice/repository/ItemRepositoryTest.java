package com.example.itemservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.itemservice.domain.item.Item;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ItemRepositoryTest {
    private final ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clear();
    }

    @Test
    void save() {
        // Given
        Item item = Item.builder()
            .itemName("itemA")
            .price(10000)
            .quantity(10)
            .build();

        // When
        Item saveItem = itemRepository.save(item);

        // Then
        Item findItem = itemRepository.findById(saveItem.getId());
        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findAll() {
        // Given
        Item item1 = Item.builder()
            .itemName("item1")
            .price(10000)
            .quantity(10)
            .build();

        Item item2 = Item.builder()
            .itemName("item2")
            .price(20000)
            .quantity(20)
            .build();

        itemRepository.save(item1);
        itemRepository.save(item2);

        // When
        List<Item> result = itemRepository.findAll();

        // Then
        assertThat(result)
            .hasSize(2)
            .containsExactly(item1, item2);
    }

    @Test
    void update() {
        // Given
        Item item = Item.builder()
            .itemName("itemA")
            .price(10000)
            .quantity(10)
            .build();

        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        // When
        Item updateItem = Item.builder()
            .itemName("itemB")
            .price(20000)
            .quantity(20)
            .build();

        itemRepository.update(itemId, updateItem);

        // Then
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem)
            .hasFieldOrPropertyWithValue("name", updateItem.getItemName())
            .hasFieldOrPropertyWithValue("price", updateItem.getPrice())
            .hasFieldOrPropertyWithValue("quantity", updateItem.getQuantity());
    }
}
