package com.billioncart.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.billioncart.model.Category;
import com.billioncart.payload.CategoryRequest;
import com.billioncart.payload.CategoryResponse;
import com.billioncart.service.CategoryService;

@RestController
@RequestMapping("/api/v1/admin/category")
public class CategoryController {
	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping("/add-category")
	public ResponseEntity<Map<String, Object>> addCategory(@RequestBody CategoryRequest request) {
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
	public ResponseEntity<Map<String, Object>> removeCategoryById(@PathVariable(name = "id") Long categoryId) {
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

	@GetMapping("/get-category/{id}")
	public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable(name = "id") Long categoryId) {
		Map<String, Object> res = new LinkedHashMap<>();

		try {
			CategoryResponse existingCategory = categoryService.getCategoryById(categoryId);
			res.put("message", "Category found successfully");
			res.put("Category", existingCategory);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

	@PutMapping("/update-category/{id}")
	public ResponseEntity<Map<String, Object>> updateCategory(@PathVariable(name = "id") Long categoryId,
			@RequestBody CategoryRequest request) {
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
	public Page<CategoryResponse> getCategories(@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "2") Integer size) {
		return categoryService.getAllCategories(page, size);
	}
}
