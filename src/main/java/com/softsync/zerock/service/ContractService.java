package com.softsync.zerock.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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


	    
	    
	 public List<Item> getAllItems() {
	        return itemRepository.findAll();
	    }
	 


    public void saveContract(Contract contract, MultipartFile contractFile) {
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
    
    
	public String saveFile(MultipartFile file) {
		// 저장할 디렉토리 경로
		String uploadDir = "C:/springMVC/sscFile";

		// 파일이 비어있는지 검사
		if (file.isEmpty()) {
			// 파일이 비어있으면 경고 로그를 남기고 null 반환
			System.out.println("Failed to save file: File is empty.");
			return null;
		}

		try {
			// 업로드 디렉토리가 존재하지 않으면 생성
			Path dirPath = Paths.get(uploadDir);
			if (!Files.exists(dirPath)) {
				Files.createDirectories(dirPath);
			}

			// 파일명 중복을 피하기 위해 유니크한 파일명 생성
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

			// 파일 경로 설정
			Path filePath = Paths.get(uploadDir, fileName);

			// 파일 저장
			Files.write(filePath, file.getBytes());

			// 파일 저장된 경로를 반환
			return filePath.toString();
		} catch (IOException e) {
			// 파일 저장 중 오류 발생 시 예외 처리
			e.printStackTrace();
			return null;
		}
	}    
	
	//계약 확정(계약하기)
	public void contractOn(int contract_number) {
	    // 서비스에서 리포지터리를 통해 계약을 확정시킴
	    Optional<Contract> contractOptional = contractRepository.findById(contract_number);
	    if (contractOptional.isPresent()) {
	        Contract contractS = contractOptional.get();
	        contractS.setContract_yn('y'); // 계약 여부를 'y'로 설정
	        contractRepository.save(contractS); // 변경된 계약 정보 저장
	    } else {
	        // ID에 해당하는 계약을 찾을 수 없는 경우에 대한 처리
	    }
	}    
   
}
