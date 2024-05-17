package com.softsync.zerock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Company;
import com.softsync.zerock.entity.Contract;
import com.softsync.zerock.repository.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired
    private CompanyRepository companyRepository;
	
	 //(계약) 업체명을 사용하여 해당 업체의 정보 조회
    public Company getCompanyInfo(String brn) {
    	 return companyRepository.findByBrn(brn);
    }

	public Company getCompanyByContract(Contract contract) {
		// TODO Auto-generated method stub
		return null;
	}
}
