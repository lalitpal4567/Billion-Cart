package com.billioncart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billioncart.model.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long>{
	Optional<Subcategory> findByName(String name);
}
