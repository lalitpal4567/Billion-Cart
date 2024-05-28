package com.billioncart.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.billioncart.model.Product;
import com.billioncart.payload.ProductRequest;
import com.billioncart.service.ProductService;

@RestController
@RequestMapping("/api/v1/admin/product")
public class ProductController {
	private ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@PostMapping("/add-product")
	public ResponseEntity<Map<String, Object>> addProduct(@RequestBody ProductRequest request){
		Map<String, Object> res = new LinkedHashMap<>();
		
		try {
			Product createdProduct = productService.addProduct(request);
			res.put("message", "Product added successfully");
			res.put("Product", createdProduct);
			return ResponseEntity.status(HttpStatus.CREATED).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
	
	@DeleteMapping("/remove-product/{id}")
	public ResponseEntity<Map<String, Object>> removeProduct(@PathVariable(name = "id") Long productId){
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
	public ResponseEntity<Map<String, Object>> getProductById(@PathVariable(name = "id") Long productId){
		Map<String, Object> res = new LinkedHashMap<>();
		
		try {
			Product existingProduct = productService.getProductById(productId);
			res.put("message", "Product found successfully");
			res.put("Product", existingProduct);
			return ResponseEntity.status(HttpStatus.FOUND).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
	
	@GetMapping("/product-list")
	public ResponseEntity<Map<String, Object>> getAllProducts() {
		Map<String, Object> res = new HashMap<>();

		List<Product> list = productService.getAllProducts();
		res.put("Products", list);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
}
