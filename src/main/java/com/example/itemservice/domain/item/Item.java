package com.example.itemservice.domain.item;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Item {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public void modifyBy(Item updateItem) {
        this.itemName = updateItem.getItemName();
        this.price = updateItem.getPrice();
        this.quantity = updateItem.getQuantity();
    }
}
