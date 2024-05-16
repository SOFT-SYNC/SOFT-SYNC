package com.softsync.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.softsync.zerock.Request.CompanyRequest;
import com.softsync.zerock.entity.Company;
import com.softsync.zerock.service.CompanyService;


//계약등록 페이지 - 업체정보 조회를 위한 컨트롤러

@RestController
public class CompanyController {
	   @Autowired
	    private CompanyService companyService;

	    @PostMapping("/getCompanyInfo")
	    public ResponseEntity<Company> getCompanyInfo(@RequestBody CompanyRequest request) {
	        //(계약) 업체명을 사용하여 해당 업체의 정보 조회
	        Company companyInfo = companyService.getCompanyInfo(request.getBrn());
	        return ResponseEntity.ok(companyInfo);
	    }
}
