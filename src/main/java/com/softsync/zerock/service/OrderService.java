package com.softsync.zerock.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Company;
import com.softsync.zerock.entity.Contract;
import com.softsync.zerock.entity.Item;
import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.repository.CompanyRepository;
import com.softsync.zerock.repository.ContractRepository;
import com.softsync.zerock.repository.ItemRepository;
import com.softsync.zerock.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	  
    @Autowired
    private ContractRepository contractRepository;
    
    @Autowired
    private CompanyRepository companyRepository;

    public List<Contract> getContractInfo(String itemCode) {
        List<Contract> contracts = contractRepository.findAllByItemItemCode(itemCode);
        return contracts;
    }
	/*
	 * public Map<String, Object> getContractInfo(String itemCode) { Map<String,
	 * Object> response = new HashMap<>();
	 * 
	 * Contract contract = contractRepository.findByItemItemCode(itemCode); if
	 * (contract != null) { response.put("contract", contract);
	 * 
	 * // 회사 정보 가져오기 Company company = companyRepository.findByContracts(contract);
	 * if (company != null) { response.put("company", company); } }
	 * 
	 * return response; }
	 */

	public List<Item> getAllItems(){
		return itemRepository.findAll();
	}
	public List<Contract> getAllContracts(){
		return contractRepository.findAll();
	}
	

	public Company getorderByBrn(String brn) {
		Company company = companyRepository.findByBrn(brn);
    	return company;
	}
	
	public Item getItemByItemCode(String itemCode) {
		Item item = itemRepository.findByItemCode(itemCode);
		return item;
	}
	

	public String generateOrderNo() {
   
        return "ORD-" + System.currentTimeMillis();
    }
	
	public void saveOrder(Orders order) {
        orderRepository.save(order);
    }

	/*
	 * public Contract getContractByContractNumber(int contractNumber) { Contract
	 * contract = contractRepository.findByContractNumber(contractNumber); return
	 * contract; }
	 */
	
	

}
 