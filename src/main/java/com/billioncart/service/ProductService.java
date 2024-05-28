package com.billioncart.service;

import java.util.List;

import com.billioncart.model.Product;
import com.billioncart.payload.ProductRequest;

public interface ProductService {
	Product addProduct(ProductRequest request);
	void removeProduct(Long productId);
	Product getProductById(Long productId);
	List<Product> getAllProducts();
}
