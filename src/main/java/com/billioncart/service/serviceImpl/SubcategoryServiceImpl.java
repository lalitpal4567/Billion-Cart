package com.billioncart.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billioncart.exception.CategoryNotFoundException;
import com.billioncart.exception.ResourceNotFoundException;
import com.billioncart.exception.SubcategoryNotFoundException;
import com.billioncart.mapper.CategoryResponseMapper;
import com.billioncart.mapper.SpecificationNameRequestMapper;
import com.billioncart.mapper.SpecificationNameResponseMapper;
import com.billioncart.mapper.SpecificationNameValueResponseMapper;
import com.billioncart.mapper.SubcategoryImageRequestMapper;
import com.billioncart.mapper.SubcategoryRequestMapper;
import com.billioncart.mapper.SubcategoryResponseMapper;
import com.billioncart.model.Category;
import com.billioncart.model.SpecificationName;
import com.billioncart.model.Subcategory;
import com.billioncart.model.SubcategoryImage;
import com.billioncart.payload.CategoryResponse;
import com.billioncart.payload.SpecificationNameRequest;
import com.billioncart.payload.SpecificationNameResponse;
import com.billioncart.payload.SubcategoryImageRequest;
import com.billioncart.payload.SubcategoryRequest;
import com.billioncart.payload.SubcategoryResponse;
import com.billioncart.repository.CategoryRepository;
import com.billioncart.repository.SubcategoryImageRepository;
import com.billioncart.repository.SubcategoryRepository;
import com.billioncart.service.SubcategoryService;

import jakarta.transaction.TransactionScoped;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {
	private SubcategoryRepository subcategoryRepository;
	private CategoryRepository categoryRepository;
	private SubcategoryImageRepository subcategoryImageRepository;

	public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository,
			SubcategoryImageRepository subcategoryImageRepository) {
		this.subcategoryRepository = subcategoryRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	@Transactional
	public SubcategoryResponse addSubcategory(Long categoryId, SubcategoryRequest request) {
		Category existingCategory = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category does not exist"));

		Optional<Subcategory> existingSubcategory = subcategoryRepository.findByName(request.getName());
		if (existingSubcategory.isPresent()) {
			throw new ResourceNotFoundException("Subcategory already exists.");
		}

		Subcategory newSubcategory = SubcategoryRequestMapper.INSTANCE.toEntity(request);
		newSubcategory.setCategory(existingCategory);
		
		List<SubcategoryImage> subcategoryImages = getSubcategoryImages(request, newSubcategory);
	    newSubcategory.setSubcategoryImages(subcategoryImages);
	    
		Subcategory updatedSubcategory = subcategoryRepository.save(newSubcategory);
		SubcategoryResponse response = SubcategoryResponseMapper.INSTANCE.toPayload(updatedSubcategory);
		CategoryResponse categoryResponse = CategoryResponseMapper.INSTANCE.toPayload(existingCategory);
		response.setCategory(categoryResponse);
		return response;
	}
	
	private static List<SubcategoryImage> getSubcategoryImages(SubcategoryRequest request, Subcategory newSubcategory){
		List<SubcategoryImageRequest> lists = request.getSubcategoryImages();
		return lists.stream().map(url -> {
			SubcategoryImage image = SubcategoryImageRequestMapper.INSTANCE.toEntity(url);
			image.setSubcategory(newSubcategory);
			return image;
		}).collect(Collectors.toList());
	}
	
	@Override
	public void removeSubcategoryById(Long subcategoryId) {
		Subcategory existingSubcategory = subcategoryRepository.findById(subcategoryId).orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found"));
		subcategoryRepository.deleteById(subcategoryId);
	}
	
	public SubcategoryResponse updateSubcategory(Long subcategoryId, SubcategoryRequest request) {
		Subcategory existingSubcategory = subcategoryRepository.findById(subcategoryId).orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found"));
		
		Subcategory subcategory = SubcategoryRequestMapper.INSTANCE.toEntity(request);
		subcategory.setSubcategoryId(existingSubcategory.getSubcategoryId());
		subcategory.setCategory(existingSubcategory.getCategory());

		subcategoryImageRepository.deleteAll(existingSubcategory.getSubcategoryImages());
		List<SubcategoryImage> subcategoryImages = getSubcategoryImages(request, subcategory);
		
		subcategory.setSubcategoryImages(subcategoryImages);
		subcategory.setProducts(existingSubcategory.getProducts());
		
		Subcategory updateSubcategory = subcategoryRepository.save(subcategory);
		SubcategoryResponse response = SubcategoryResponseMapper.INSTANCE.toPayload(updateSubcategory);
		return response;
	}
	
	@Override
	public SubcategoryResponse getSubcategoryById(Long subcategoryId) {
		Subcategory existingSubcategory = subcategoryRepository.findById(subcategoryId).orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found"));
		
		return SubcategoryResponseMapper.INSTANCE.toPayload(existingSubcategory);
	}
	
	@Transactional
	public Page<SubcategoryResponse> getAllSubcategories(Integer page, Integer size){
		Page<Subcategory> subcategories = subcategoryRepository.findAll(PageRequest.of(page, size));

		Page<SubcategoryResponse> subcategoryRespPage = subcategories.map(subcat ->{
			SubcategoryResponse response = SubcategoryResponseMapper.INSTANCE.toPayload(subcat);
			return response;
		});
		return subcategoryRespPage;
	}
	
	private List<SpecificationNameResponse> getAllSpecificationNames(Subcategory subcat) {
		List<SpecificationNameResponse> specificationNames = subcat.getSpecificationNames().stream().map(name ->{
			SpecificationNameResponse specificationNameResponse = SpecificationNameResponseMapper.INSTANCE.toPayload(name);
			return specificationNameResponse;
		}).collect(Collectors.toList());
		return specificationNames;
	}
	
	@Transactional
	@Override
	public Page<SubcategoryResponse> getSubcategoriesByCategoryId(Integer page, Integer size, Long categoryId){
		Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
		Page<Subcategory> subcatePage = subcategoryRepository.findAllByCategory(PageRequest.of(page, size), category);
		
		Page<SubcategoryResponse> subcategoryRespPage = subcatePage.map(subcat ->{
			SubcategoryResponse response = SubcategoryResponseMapper.INSTANCE.toPayload(subcat);
			return response;
		});
		return subcategoryRespPage;
	}
}
