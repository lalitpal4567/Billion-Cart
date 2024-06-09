package com.billioncart.service;

import org.springframework.data.domain.Page;

import com.billioncart.model.Category;
import com.billioncart.payload.SubcategoryRequest;
import com.billioncart.payload.SubcategoryResponse;

public interface SubcategoryService {
	SubcategoryResponse addSubcategory(Long categoryId, SubcategoryRequest request);
	
	void removeSubcategoryById(Long subcategoryId);
	
	SubcategoryResponse updateSubcategory(Long subcategoryId, SubcategoryRequest request);
	
	SubcategoryResponse getSubcategoryById(Long subcategoryId);
	
	Page<SubcategoryResponse> getAllSubcategories(Integer page, Integer size);
	
	Page<SubcategoryResponse> getSubcategoriesByCategoryId(Integer page, Integer size, Long categoryId);
}
