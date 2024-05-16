package com.softsync.zerock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softsync.zerock.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, String>{
	 //(계약) 업체명을 사용하여 해당 업체의 정보 조회
	Company findByBrn(String brn);
	
}	
