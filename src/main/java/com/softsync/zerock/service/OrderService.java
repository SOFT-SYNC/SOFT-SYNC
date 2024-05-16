package com.softsync.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Company;
import com.softsync.zerock.entity.Item;
import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.repository.CompanyRepository;
import com.softsync.zerock.repository.ItemRepository;
import com.softsync.zerock.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	

	public List<Orders> getAllOrders() {
		return orderRepository.findAll();
	}

	public List<Item> getAllItems() {
	        return itemRepository.findAll();
	    }

	public Company getorderByBrn(String brn) {
		Company company = companyRepository.findByBrn(brn);
    	return company;
	}
	  public Item getItemByItemCode(String itemCode) {
	        return itemRepository.findByItemCode(itemCode); 
	    }
	

}
 