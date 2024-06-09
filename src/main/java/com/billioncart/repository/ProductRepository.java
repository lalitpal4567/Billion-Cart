package com.billioncart.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.billioncart.model.Product;
import com.billioncart.model.Subcategory;

public interface ProductRepository extends JpaRepository<Product, Long>{
	Page<Product> findAll(Pageable pageable);
	
	Page<Product>  findAllBySubcategory(Pageable pageable, Subcategory subcategory);
}
