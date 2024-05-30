package com.softsync.zerock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softsync.zerock.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

	Contract findByItemItemCode(String itemCode);

	List<Contract> findAllByItemItemCode(String itemCode);

	

    
	 
}