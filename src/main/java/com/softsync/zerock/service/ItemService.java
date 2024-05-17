package com.softsync.zerock.service;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.softsync.zerock.DTO.ItemDto;
import com.softsync.zerock.entity.Category;
import com.softsync.zerock.entity.Company;
import com.softsync.zerock.entity.Contract;
import com.softsync.zerock.entity.Item;
import com.softsync.zerock.repository.CategoryRepository;
import com.softsync.zerock.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryService categoryService;

	public Item saveItem(Item item) {
		return itemRepository.save(item);
	}

	public Item addItem(ItemDto itemDto) {
		Item item = new Item();
		item.setDimensions(itemDto.getDimensions());
		item.setItemName(itemDto.getItemName());
		item.setMaterial(itemDto.getMaterial());
		String savedFilePath = saveFile(itemDto.getBlueprintPath());
		item.setBlueprintPath(savedFilePath);
		// 소분류 카테고리를 ID로 찾아서 아이템에 설정
		Category childcategory = categoryRepository.findById(itemDto.getChildcategoryId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid subcategory ID"));
		item.setCategory(childcategory);

		Category midCategory = childcategory.getParent();
		Category topCategory = midCategory.getParent();

		// 각 카테고리의 이름 대신 알파벳 코드 사용
		String topCategoryCode = categoryService.getCategoryCode(topCategory);
		String midCategoryCode = categoryService.getCategoryCode(midCategory);
		String childCategoryCode = categoryService.getCategoryCode(childcategory);
		String uniqueCode = generateUniqueCode();
		String itemCode = String.format("%s%s%s-%s", topCategoryCode, midCategoryCode, childCategoryCode, uniqueCode);
		item.setItemCode(itemCode);

		return itemRepository.save(item);
	}

	public String generateUniqueCode() {
	    try {
	        String timestamp = String.valueOf(System.currentTimeMillis());
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(timestamp.getBytes(StandardCharsets.UTF_8));
	        String hex = String.format("%064x", new BigInteger(1, hash));
	        return hex.substring(0, 6);  // 첫 6자리만 사용
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("No such algorithm exception", e);
	    }
	}

	// 파일 저장 로직
	private String saveFile(MultipartFile file) {
		// 저장할 디렉토리 경로
		String uploadDir = "C:\\springMVC\\sscFile";

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
		
	

	

	public List<Company> getAllCompaniesForItemByItemCode(String itemCode) {
		Item item = itemRepository.findByItemCode(itemCode);
        List<Company> companies = new ArrayList<>();
        if (item != null) {
            List<Contract> contracts = item.getContracts();
            for (Contract contract : contracts) {
                companies.add(contract.getCompany());
            }
        }
        return companies;
    }



}