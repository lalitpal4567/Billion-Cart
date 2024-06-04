package com.billioncart.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billioncart.exception.CategoryNotFoundException;
import com.billioncart.exception.ResourceNotFoundException;
import com.billioncart.mapper.CategoryRequestMapper;
import com.billioncart.mapper.CategoryResponseMapper;
import com.billioncart.model.Category;
import com.billioncart.payload.CategoryRequest;
import com.billioncart.payload.CategoryResponse;
import com.billioncart.repository.CategoryRepository;
import com.billioncart.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	private CategoryRepository categoryRepository;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public CategoryResponse addCategory(CategoryRequest request) {
		Optional<Category> existingCategory = categoryRepository.findByName(request.getName());
		
		if(!existingCategory.isPresent()) {
			Category newCategory = CategoryRequestMapper.INSTANCE.toEntity(request);
			Category createdCategory = categoryRepository.save(newCategory);
			return CategoryResponseMapper.INSTANCE.toPayload(createdCategory);			
		}else {
			throw new RuntimeException("Category already exists.");
		}
	}
	

	@Transactional
	@Override
	public void removeCategoryById(Long categoryId) {
		Category existingCatgory = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category does not exist."));
		categoryRepository.deleteById(categoryId);
	}

	
	@Override
	public CategoryResponse updateCategory(Long categoryId, CategoryRequest request) {
		Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
		
		existingCategory.setName(request.getName());
		existingCategory.setDescription(request.getDescription());
		existingCategory.setImageUrl(request.getImageUrl());
		
		Category updatedCategory = categoryRepository.save(existingCategory);
		return CategoryResponseMapper.INSTANCE.toPayload(updatedCategory);
	}
	
	@Override
	public CategoryResponse getCategoryById(Long categoryId) {
		Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
		
		return CategoryResponseMapper.INSTANCE.toPayload(existingCategory);
	}
	
	
	@Override
	 public Page<CategoryResponse> getAllCategories(Integer page, Integer size) {
	        Page<Category> categories = categoryRepository.findAll(PageRequest.of(page, size));
	        
	        Page<CategoryResponse> categoryResponsePage = categories.map(cat ->{
	        	CategoryResponse response = CategoryResponseMapper.INSTANCE.toPayload(cat);
	        	return response;
	        });
	        return categoryResponsePage;
	    }
}
