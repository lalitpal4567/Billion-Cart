package com.billioncart.service;

import java.util.List;

import com.billioncart.model.Product;
import com.billioncart.payload.ProductRequest;
import com.billioncart.payload.ProductResponse;

public interface ProductService {
	ProductResponse addProduct(ProductRequest request);
	void removeProduct(Long productId);
	Product getProductById(Long productId);
	List<Product> getAllProducts();
}
