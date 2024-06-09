package com.billioncart.service;

import org.springframework.data.domain.Page;

import com.billioncart.payload.ProductRequest;
import com.billioncart.payload.ProductResponse;

public interface ProductService {
	ProductResponse addProduct(Long subcategoryId, ProductRequest request);
	
	void removeProduct(Long productId);
	
	ProductResponse getProductById(Long productId);
	
	Page<ProductResponse> getProductBySubcategoryId(Long subcategoryId, Integer page, Integer size);
	
	Page<ProductResponse> getAllProducts(Integer page, Integer size);
}
