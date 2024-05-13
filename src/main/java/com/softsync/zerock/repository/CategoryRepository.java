package com.softsync.zerock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softsync.zerock.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByParentId(Long parentId);
    
    List<Category> findByParentIdIsNull();
}
