package com.billioncart.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.billioncart.model.Category;
import com.billioncart.model.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long>{
	Optional<Subcategory> findByName(String name);
	
	Page<Subcategory> findAll(Pageable pageable);
	
	Page<Subcategory> findAllByCategory(Pageable pageable, Category category);

}
