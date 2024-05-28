package com.billioncart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billioncart.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
