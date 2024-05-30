package com.softsync.zerock.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softsync.zerock.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

	Contract findByItemItemCode(String itemCode);

	List<Contract> findAllByItemItemCode(String itemCode);
	
	Long countByContractYn(char contractYn);

	@Query("SELECT COUNT(*) FROM Contract  WHERE contract_date BETWEEN :startDate AND :endDate")
	Long countByContractDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	 
}