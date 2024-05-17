package com.softsync.zerock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softsync.zerock.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	
    Item findByItemNameAndItemCode(String itemName, String itemCode);
    
    Item findByItemCode(String itemCode);
    
}
