package com.softsync.zerock.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Category;
import com.softsync.zerock.repository.CategoryRepository;

@Service
public class CategoryService {
	private Map<String, String> categoryCodeMap = new HashMap<>();

	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryService() {
		// 카테고리 코드 매핑 초기화
		categoryCodeMap.put("전기자재", "EL");
		categoryCodeMap.put("전력선", "CP");
		categoryCodeMap.put("전선", "KT"); 
		
	}

	String getCategoryCode(Category category) {
		// 카테고리 이름에 매핑된 코드를 반환
		String code = categoryCodeMap.get(category.getName());
		if (code == null) {
			throw new IllegalArgumentException("No code defined for category: " + category.getName());
		}
		return code;
	}

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<Category> findAllCategories() {
		return categoryRepository.findAll();
	}
}