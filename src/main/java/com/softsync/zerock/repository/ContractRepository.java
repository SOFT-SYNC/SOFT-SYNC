package com.softsync.zerock.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softsync.zerock.entity.Contract;
import com.softsync.zerock.entity.Item;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

	Contract findByItemItemCode(String itemCode);
	Contract findByItemId(Long itemId);
	List<Contract> findAllByItemItemCode(String itemCode);
	
	Long countByContractYn(char contractYn);

	Contract findByItem(Item item);

	@Query("SELECT COUNT(*) FROM Contract  WHERE contract_date BETWEEN :startDate AND :endDate")
	Long countByContractDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	
	    
	    Page<Contract> findByItem_ItemNameContaining(String itemName, Pageable pageable);
	    Page<Contract> findByItem_ItemCodeContaining(String itemCode, Pageable pageable);
		Page<Contract> findByOrders_OrderNoContaining(String searchKeyword, Pageable pageable);
	 
}