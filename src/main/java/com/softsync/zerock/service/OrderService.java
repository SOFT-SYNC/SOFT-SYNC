package com.softsync.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Company;
import com.softsync.zerock.entity.Contract;
import com.softsync.zerock.entity.Item;
import com.softsync.zerock.entity.Orders;
import com.softsync.zerock.repository.CompanyRepository;
import com.softsync.zerock.repository.ContractRepository;
import com.softsync.zerock.repository.ItemRepository;
import com.softsync.zerock.repository.OrderRepository;
import com.softsync.zerock.repository.ProcurementPlanRepository;

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
    
    @Autowired
    ProcurementPlanRepository procurementPlanRepository;
    
//    @Autowired
//    InspectionRepository inspectionRepository;


	public List<Item> getAllItems(){
		return itemRepository.findAll();
	}
	public List<Contract> getAllContracts(Pageable pageable){
		return contractRepository.findAll();
	}
	public List<Orders> getAllOrders() {
		return orderRepository.findAll();
	}
	
	public List<Company> getAllCompany() {
		return companyRepository.findAll();
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
		 // 현재 시간을 밀리초 단위로 가져옵니다.
        long currentTimeMillis = System.currentTimeMillis();
        // 밀리초를 1000으로 나누어 초 단위로 변환한 후, 1000000으로 모듈로 연산을 합니다.
        long uniquePart = currentTimeMillis % 1000000;
        // uniquePart가 6자리가 되도록 zero-padding
        String orderNo = String.format("ORD-%06d", uniquePart);
        return orderNo;
    }
	
	public void saveOrder(Orders order) {
        orderRepository.save(order);
    }

	 public Page<Orders> getOrders(Pageable pageable) {
	        return orderRepository.findAll(pageable);
	    }
	public List<Contract> getAllContracts() {
		return contractRepository.findAll();
		}

	public Orders getOrderDetailsByOrderNo(String orderNo) {
        return orderRepository.findByOrderNo(orderNo);
    }
	
	//발주현황 그래프~
	public Long[] trackingCount(){
		System.out.println("발주 서비스 : 발주현황 그래프");
		
		Long proc = procurementPlanRepository.count(); //총 조달계획
		
		
		Long order = orderRepository.countByReceiptYn("Y"); //발주서 발행
		if (order == null) {
		    order = 0L;
		}
		
		
		Long arr[] = {proc, order}; 
		
		return arr;
	}
	



}
 