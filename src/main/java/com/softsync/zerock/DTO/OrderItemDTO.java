package com.softsync.zerock.DTO;

import com.softsync.zerock.entity.Item;
import com.softsync.zerock.entity.Orders;
 
public class OrderItemDTO {
    
    private Orders order;
    private Item item;

    public OrderItemDTO(Orders order, Item item) {
        this.order = order;
        this.item = item;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
