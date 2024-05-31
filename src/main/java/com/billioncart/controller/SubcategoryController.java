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

import com.billioncart.model.Subcategory;
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
	
	@DeleteMapping("/remove-subcategory/{id}")
	public ResponseEntity<Map<String, Object>> removeSubcategoryById(@PathVariable(name = "id") Long subcategoryId){
		Map<String, Object> res = new LinkedHashMap<>();
		
		try {
			subcategoryService.removeSubcategoryById(subcategoryId);
			res.put("message", "Subcategory removed successfully");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
	
	@PutMapping("/update-subcategory/{id}")
	public ResponseEntity<Map<String, Object>> updateSubcategory(@PathVariable(name = "id") Long subcategoryId, @RequestBody SubcategoryRequest request){
		Map<String, Object> res = new LinkedHashMap<>();
		
		try {
			SubcategoryResponse updatedSubcategory = subcategoryService.updateSubcategory(subcategoryId, request);
			res.put("message", "Subcategory updated successfully");
			res.put("Subcategory", updatedSubcategory);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
	
	
	@GetMapping("/subcategories-list")
	public ResponseEntity<Map<String, Object>> getAllSubcategories(){
		Map<String, Object> res = new LinkedHashMap<>();
		try {
			List<SubcategoryResponse> list = subcategoryService.getAllSubcategories();
			res.put("Subcategories", list);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
	
}
