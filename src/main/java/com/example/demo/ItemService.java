package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ItemService {
	@Autowired
	ItemDao itemDao;
	
    public void addItem(ItemDto item) {
    	System.out.println("[아이템 서비스]품목 신규등록");
        itemDao.save(item);
    }
    
    public List<ItemDto> getAllItems() {
        return itemDao.findAll();
    }
}
