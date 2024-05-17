package com.softsync.zerock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softsync.zerock.entity.Category;
import com.softsync.zerock.repository.CategoryRepository;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // 대분류 카테고리만 가져오는 API
    @GetMapping("/root")
    public ResponseEntity<List<Category>> getRootCategories() {
        List<Category> rootCategories = categoryRepository.findByParentIdIsNull();
        return ResponseEntity.ok(rootCategories);
    }

    // 특정 카테고리의 하위 카테고리를 가져오는 API
    @GetMapping("/{parentId}/subcategories")
    public ResponseEntity<List<Category>> getSubcategories(@PathVariable Long parentId) {
        List<Category> subcategories = categoryRepository.findAllByParentId(parentId);
        return ResponseEntity.ok(subcategories);
    }
}