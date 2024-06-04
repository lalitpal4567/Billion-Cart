package com.billioncart.service;

import org.springframework.data.domain.Page;

import com.billioncart.payload.SubcategoryRequest;
import com.billioncart.payload.SubcategoryResponse;

public interface SubcategoryService {
	SubcategoryResponse addSubcategory(SubcategoryRequest request);
	
	void removeSubcategoryById(Long subcategoryId);
	
	SubcategoryResponse updateSubcategory(Long subcategoryId, SubcategoryRequest request);
	
	Page<SubcategoryResponse> getAllSubcategories(Integer page, Integer size);
}
