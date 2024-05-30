package com.billioncart.service;

import com.billioncart.payload.SubcategoryRequest;
import com.billioncart.payload.SubcategoryResponse;

public interface SubcategoryService {
	SubcategoryResponse addSubcategory(SubcategoryRequest request);
	void removeSubcategoryById(Long subcategoryId);
}
