package com.softsync.zerock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softsync.zerock.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    
	
}