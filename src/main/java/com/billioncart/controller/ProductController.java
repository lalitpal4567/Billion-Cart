package com.billioncart.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.billioncart.model.Product;
import com.billioncart.payload.ProductRequest;
import com.billioncart.payload.ProductResponse;
import com.billioncart.service.ProductService;

@RestController
@RequestMapping("/api/v1/admin/product")
public class ProductController {
	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping("/add-product/{id}")
	public ResponseEntity<Map<String, Object>> addProduct(@PathVariable(name = "id") Long subcategoryId,
			@RequestBody ProductRequest request) {
		Map<String, Object> res = new LinkedHashMap<>();

		try {
			ProductResponse createdProduct = productService.addProduct(subcategoryId, request);
			res.put("message", "Product added successfully");
			res.put("Product", createdProduct);
			return ResponseEntity.status(HttpStatus.CREATED).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

	@DeleteMapping("/remove-product/{id}")
	public ResponseEntity<Map<String, Object>> removeProduct(@PathVariable(name = "id") Long productId) {
		Map<String, Object> res = new LinkedHashMap<>();

		try {
			productService.removeProduct(productId);
			res.put("message", "Product removed successfully");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

	@GetMapping("/fetch-product/{id}")
	public ResponseEntity<Map<String, Object>> getProductById(@PathVariable(name = "id") Long productId) {
		Map<String, Object> res = new LinkedHashMap<>();

		try {
			ProductResponse existingProduct = productService.getProductById(productId);
			res.put("message", "Product found successfully");
			res.put("Product", existingProduct);
			return ResponseEntity.status(HttpStatus.FOUND).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

	@GetMapping("/product-subcategory-wise/{id}")
	public Page<ProductResponse> getAllProductsBySubcategoryId(@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "2") Integer size, @PathVariable(name = "id") Long subcategoryId) {

		return productService.getProductBySubcategoryId(subcategoryId, page, size);
	}
	
	@GetMapping("/product-list")
	public Page<ProductResponse> getAllProducts(@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "2") Integer size) {

		return productService.getAllProducts(page, size);
	}
}
