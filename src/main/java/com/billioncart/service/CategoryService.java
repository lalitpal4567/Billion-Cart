package com.billioncart.service;

import java.util.List;

import com.billioncart.model.Category;
import com.billioncart.payload.CategoryRequest;
import com.billioncart.payload.CategoryResponse;

public interface CategoryService {
	CategoryResponse addCategory(CategoryRequest request);
	void removeCategoryById(Long categoryId);
	CategoryResponse updateCategory(Long categoryId, CategoryRequest request);
	List<Category> getAllCategories();
}
