package com.softsync.zerock.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import com.softsync.zerock.repository.InspectionRepository;
import com.softsync.zerock.repository.InvoiceRepository;
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
    
    @Autowired
    InvoiceRepository invoiceRepository;
    
    @Autowired
    InspectionRepository inspectionRepository;
    



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

	 public Orders getOrderDetails(String orderNo) {
	       return orderRepository.findById(orderNo).orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + orderNo));
	   }
	 public List<Orders> getAllOrders1() {
	       return orderRepository.findAll();
	   }

	
	//발주현황 그래프~
	public Long[] trackingCount(){
		System.out.println("발주 서비스 : 발주현황 그래프");
		
		
		Long contract = contractRepository.countByContractYn('y'); //계약서 발행 확인용
		Long order = orderRepository.countByReceiptYn("Y"); //발주서 발행
		Long invoice = invoiceRepository.countByPublishYn('Y');  //CHAR 타입..
		Long inspect = inspectionRepository.countByinspecYn();
		System.out.println(order);
		
		Long arr[] = {contract, order, inspect, invoice}; 
		
		return arr;
	}
	
	
	//기간 검색
	public Long[] search(LocalDate startDate,LocalDate endDate ){
		Long contract =	contractRepository.countByContractDate(startDate, endDate); //계약
		Long order =orderRepository.countByOrderDate(startDate, endDate); //발주
		Long invoice = invoiceRepository.countByInvoiceDate(startDate, endDate);
		Long inspect = inspectionRepository.countByInspecPlanDate(startDate, endDate);
		System.out.println(inspect);
		Long arr[] = {contract, order, inspect, invoice}; 
		return arr;
	}
	
	public void getOrder(String no) {
		
		Orders order = orderRepository.getReferenceById(no);
		
        
		if(order.getInspectYNG() == 'G') {
			order.setInspectYNG('Y');
			orderRepository.save(order);
		}
		
	}

	/*
	 * public int getOrdersQuantityByDate(LocalDate date) { int ordersQuantity =
	 * orderRepository.getOrdersQuantityByDate(date);
	 * System.out.println("Today's orders quantity: " + ordersQuantity); return
	 * ordersQuantity; }
	 */
	 public List<Orders> getOrdersByDate(LocalDate date) {
	        return orderRepository.findByOrderDate(date);
	    }
	
	public int getTotalOrderQuantityByDate(LocalDate date) {
        List<Orders> orders = getOrdersByDate(date);
        return orders.stream().mapToInt(Orders::getOrderQuantity).sum();
    }

	   public List<Orders> getOrdersByReceiveDuedate(LocalDate localDate) {
	        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	        return orderRepository.findByReceiveDuedate(date);
	    }

	    public int getTotalOrderQuantityByReceiveDuedate(LocalDate localDate) {
	        List<Orders> orders = getOrdersByReceiveDuedate(localDate);
	        return orders.stream().mapToInt(Orders::getOrderQuantity).sum();
	    }
}
 