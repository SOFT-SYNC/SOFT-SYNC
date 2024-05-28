package com.softsync.zerock.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.softsync.zerock.entity.Company;
import com.softsync.zerock.entity.Contract;
import com.softsync.zerock.entity.Item;
import com.softsync.zerock.service.ContractService;

@Controller
public class ContractController {
	
	@Autowired
	ContractService contractService;

	//계약서 리스트 조회 + 저장페이지(베이직컨트롤러에서 옮김)
	@GetMapping("/add_contract")   
	public String addcontractview(Model model, @PageableDefault(size = 10) Pageable pageable) {
		System.out.println("[계약컨트롤러] 계약서 리스트");
		
		Page<Item> items = contractService.getAllItems(pageable);
	       model.addAttribute("items", items);
		
		return"procurement/add_contract";
	}
	
	
	//계약 정보 저장
	 @PostMapping("/saveContract")
	    public String saveContract(@RequestParam("contract_path") MultipartFile contractFile,
	                               @RequestParam("brn") String brn,
	                               @RequestParam("company_name") String companyName,
	                               @RequestParam("company_account") String companyAccount,
	                               @RequestParam("company_note") String companyNote,
	                               @RequestParam("itemCode") String itemCode,
	                               @RequestParam("itemName") String itemName,
	                               @RequestParam("price") int price,
	                               @RequestParam("leadTime") LocalDate leadTime,
	                               @RequestParam("contractNote") String contractNote,
	                               Model model,
	                               @PageableDefault(size = 10) Pageable pageable) {
			System.out.println("[계약컨트롤러] 계약서 저장");
			 
	        Contract contract = new Contract();
	        Company company = contractService.getContractByBrn(brn);//회사정보 가져오기(외래키)
	        Item item = contractService.getItemByItemCode(itemCode);//품목정보 가져오기(외래키)
	        
	        String file = contractService.saveFile(contractFile);
	       
	        
	        //저장
	        contract.setCompany(company); //회사정보(외래키)
	        contract.setContract_date(LocalDate.now());  //계약일 (현재시각)
	        contract.setContract_path(file);
	        contract.setItem(item);//품목정보(외래키)
	        contract.setLead_time(leadTime);//납기일
	        contract.setUnit_price(price);//단가
	        contract.setContract_yn('n');//계약여부
			/* contract.setContractSaveName(contractFile); */
	        
	        contractService.saveContract(contract, contractFile);//인설트
	        
	        
	        // 아이템에 계약 정보 추가
	        List<Contract> contracts = item.getContracts(); // 기존 계약 리스트 가져오기
	        if (contracts == null) {
	            contracts = new ArrayList<>(); // 기존 계약 리스트가 없으면 새로 생성
	        }
	        contracts.add(contract); // 새로운 계약 추가
	        item.setContracts(contracts); // 아이템에 새로운 계약 리스트 설정

	        // 아이템 정보 저장
	        contractService.saveItem(item);

	        Page<Item> items = contractService.getAllItems(pageable);//리다이렉트용 품목리스트
		       model.addAttribute("items", items);

	        return "redirect:add_contract"; // 저장 후 홈페이지로 리다이렉트
	    } 
	
	    @GetMapping("/contractOn")
	    public String confirmContract(@RequestParam("contract_number") int contract_number,Model model,
	    								@PageableDefault(size = 10) Pageable pageable) {
	        // 컨트롤러에서 서비스를 호출하여 계약을 확정시킴
	        contractService.contractOn(contract_number);
	         
	        Page<Item> items = contractService.getAllItems(pageable);//리다이렉트용 품목리스트
		     model.addAttribute("items", items);
	        return "redirect:add_contract"; // 확정 후 계약 리스트 페이지로 리다이렉트
	    }

}
