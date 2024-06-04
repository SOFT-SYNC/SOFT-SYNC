package com.softsync.zerock.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.softsync.zerock.entity.Company;
import com.softsync.zerock.entity.Contract;
import com.softsync.zerock.entity.Item;
import com.softsync.zerock.repository.CompanyRepository;
import com.softsync.zerock.repository.ContractRepository;
import com.softsync.zerock.repository.ItemRepository;

@Service
public class ContractService {

	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	
   @Autowired
    private ContractRepository contractRepository;
   
   @Value("${file.upload-dir}")
    private String uploadDir;


	    
	    
	 public Page<Item> getAllItems(@PageableDefault(size = 10) Pageable pageable) {
	        return itemRepository.findAll(pageable);
	    }
	 


    public void saveContract(Contract contract) {
        // 계약 정보 저장
        contractRepository.save(contract);
    }
    
    
    //계약등록 시 품목(외래키)
    public Item getItemByItemCode(String itemCode) {
        return itemRepository.findByItemCode(itemCode); 
    }
    
    public Company getContractByBrn(String brn) {
    	Company company = companyRepository.findByBrn(brn);
    	return company;
    }
    
    //계약 저장(아이템의 외래키인 계약테이블을 리스트로 저장)
    public void saveItem(Item item) {
        itemRepository.save(item);
    }
    
    
    
   

	//계약 확정(계약하기)
	public void contractOn(int contract_number) {
	    // 서비스에서 리포지터리를 통해 계약을 확정시킴
	    Optional<Contract> contractOptional = contractRepository.findById(contract_number);
	    if (contractOptional.isPresent()) {
	        Contract contractS = contractOptional.get();
	        LocalDate date = LocalDate.now();
	        contractS.setContract_date(date);
	        contractS.setContractYn('y'); // 계약 여부를 'y'로 설정
	        contractRepository.save(contractS); // 변경된 계약 정보 저장
	    } else {
	        // ID에 해당하는 계약을 찾을 수 없는 경우에 대한 처리
	    }
	}



	public Contract getContractByItemId(Long itemId) {
		System.out.println(contractRepository.findByItemId(itemId));
		return contractRepository.findByItemId(itemId);
	    		 
	}

	//계약 삭제   // n 상태만 가능
	public void contractOut(int contract_number) {
		 Contract contract = contractRepository.getReferenceById(contract_number);
		        contractRepository.delete(contract); // 변경된 계약 정보 저장
	}



	public Contract getContractByItemCode(String itemCode) {
		System.out.println(contractRepository.findByItemItemCode(itemCode));
		return contractRepository.findByItemItemCode(itemCode);
	}
   
}