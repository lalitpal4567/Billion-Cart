package com.billioncart.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.billioncart.payload.CategoryRequest;
import com.billioncart.payload.CategoryResponse;
import com.billioncart.payload.SubcategoryRequest;
import com.billioncart.payload.SubcategoryResponse;
import com.billioncart.service.SubcategoryService;

@RestController
@RequestMapping("/api/v1/admin/subcategory")
public class SubcategoryController {
	private SubcategoryService subcategoryService;
	
	public SubcategoryController(SubcategoryService subcategoryService) {
		this.subcategoryService = subcategoryService;
	}
	
	@PostMapping("/add-subcategory")
	public ResponseEntity<Map<String, Object>> addSubcategory(@RequestBody SubcategoryRequest request){
		Map<String, Object> res = new LinkedHashMap<>();
		
		try {
			SubcategoryResponse createdSubcateogry = subcategoryService.addSubcategory(request);
			res.put("message", "Subcategory added successfully");
			res.put("Subcategory", createdSubcateogry);
			return ResponseEntity.status(HttpStatus.CREATED).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
}
