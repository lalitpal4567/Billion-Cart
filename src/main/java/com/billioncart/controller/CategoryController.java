package com.billioncart.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.billioncart.model.Category;
import com.billioncart.payload.CategoryRequest;
import com.billioncart.payload.CategoryResponse;
import com.billioncart.service.CategoryService;

@RestController
@RequestMapping("/api/v1/admin/categories")
public class CategoryController {
	private CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@PostMapping("/add-category")
	public ResponseEntity<Map<String, Object>> addCategory(@RequestBody CategoryRequest request){
		Map<String, Object> res = new LinkedHashMap<>();
		
		try {
			CategoryResponse createdCateogry = categoryService.addCategory(request);
			res.put("message", "Category added successfully");
			res.put("Category", createdCateogry);
			return ResponseEntity.status(HttpStatus.CREATED).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
	
	
	@DeleteMapping("/remove-category/{id}")
	public ResponseEntity<Map<String, Object>> removeCategoryById(@PathVariable(name = "id") Long categoryId){
		Map<String, Object> res = new LinkedHashMap<>();
		
		try {
			categoryService.removeCategoryById(categoryId);
			res.put("message", "Category removed successfully");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
	
	
	@PutMapping("/update-category/{id}")
	public ResponseEntity<Map<String, Object>> updateCategory(@PathVariable(name = "id") Long categoryId, @RequestBody CategoryRequest request){
		Map<String, Object> res = new LinkedHashMap<>();
		try {
			CategoryResponse updateCategory = categoryService.updateCategory(categoryId, request);
			res.put("message", "Category updated successfully");
			res.put("Category", updateCategory);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
	
	@GetMapping("/categories-list")
	public ResponseEntity<Map<String, Object>> getAllCategories(){
		Map<String, Object> res = new LinkedHashMap<>();
		try {
			List<Category> list = categoryService.getAllCategories();
			res.put("Categories", list);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
}
