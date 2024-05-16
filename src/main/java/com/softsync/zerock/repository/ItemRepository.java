package com.softsync.zerock.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.softsync.zerock.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	Item findByItemCode(String itemCode); //계약시 아이템코드 확인
	
}
