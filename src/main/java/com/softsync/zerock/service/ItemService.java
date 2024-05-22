package com.softsync.zerock.service;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.softsync.zerock.DTO.ItemDto;
import com.softsync.zerock.entity.Category;

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
	
	@Value("${file.upload-dir}")
    private String uploadDir;

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
		
		 // Item 엔터티 저장
        Item savedItem = itemRepository.save(item);

		return savedItem;
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

	private String saveFile(MultipartFile file) {
	    if (file.isEmpty()) {
	        System.out.println("Failed to save file: File is empty.");
	        return null;
	    }


	    try {
	        Path dirPath = Paths.get(uploadDir);
	        if (!Files.exists(dirPath)) {
	            Files.createDirectories(dirPath);
	        }
	        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	        Path filePath = Paths.get(uploadDir, fileName);
	        Files.write(filePath, file.getBytes());

	        // 웹 접근 가능 경로 반환
	        return "/images/" + fileName;  // URL 경로로 수정
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}