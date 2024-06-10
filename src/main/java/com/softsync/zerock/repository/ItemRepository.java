package com.softsync.zerock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.softsync.zerock.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
   Item findByItemCode(String itemCode); //계약시 아이템코드 확인
   
    Item findByItemNameAndItemCode(String itemName, String itemCode);
    
    void deleteByItemCode(String itemCode);
    
    Page<Item> findByItemCodeContaining(String itemCode, Pageable pageable);
    Page<Item> findByItemNameContaining(String itemName, Pageable pageable);
    Page<Item> findByMaterialContaining(String material, Pageable pageable);
}