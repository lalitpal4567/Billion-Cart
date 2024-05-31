package com.billioncart.service;

import java.util.List;

import com.billioncart.model.Subcategory;
import com.billioncart.payload.SubcategoryRequest;
import com.billioncart.payload.SubcategoryResponse;

public interface SubcategoryService {
	SubcategoryResponse addSubcategory(SubcategoryRequest request);
	void removeSubcategoryById(Long subcategoryId);
	SubcategoryResponse updateSubcategory(Long subcategoryId, SubcategoryRequest request);
	List<SubcategoryResponse> getAllSubcategories();
}
