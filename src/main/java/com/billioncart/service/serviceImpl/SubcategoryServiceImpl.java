package com.billioncart.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billioncart.exception.ResourceNotFoundException;
import com.billioncart.exception.SubcategoryNotFoundException;
import com.billioncart.mapper.SpecificationNameRequestMapper;
import com.billioncart.mapper.SubcategoryImageRequestMapper;
import com.billioncart.mapper.SubcategoryRequestMapper;
import com.billioncart.mapper.SubcategoryResponseMapper;
import com.billioncart.model.Category;
import com.billioncart.model.SpecificationName;
import com.billioncart.model.Subcategory;
import com.billioncart.model.SubcategoryImage;
import com.billioncart.payload.SpecificationNameRequest;
import com.billioncart.payload.SubcategoryImageRequest;
import com.billioncart.payload.SubcategoryRequest;
import com.billioncart.payload.SubcategoryResponse;
import com.billioncart.repository.CategoryRepository;
import com.billioncart.repository.SubcategoryImageRepository;
import com.billioncart.repository.SubcategoryRepository;
import com.billioncart.service.SubcategoryService;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {
	private SubcategoryRepository subcategoryRepository;
	private CategoryRepository categoryRepository;

	public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository,
			SubcategoryImageRepository subcategoryImageRepository) {
		this.subcategoryRepository = subcategoryRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	@Transactional
	public SubcategoryResponse addSubcategory(SubcategoryRequest request) {
		Category existingCategory = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category does not exist"));

		Optional<Subcategory> existingSubcategory = subcategoryRepository.findByName(request.getName());
		if (existingSubcategory.isPresent()) {
			throw new ResourceNotFoundException("Subcategory already exists.");
		}

		Subcategory newSubcategory = SubcategoryRequestMapper.INSTANCE.toEntity(request);
		newSubcategory.setCategory(existingCategory);
		
		List<SubcategoryImage> subcategoryImages = getSubcategoryImages(request, newSubcategory);
		List<SpecificationName> specificationNames = getSpecificationNames(request, newSubcategory);
	    newSubcategory.setSubcategoryImages(subcategoryImages);
	    newSubcategory.setSpecificationNames(specificationNames);
	    
	    
		Subcategory updatedSubcategory = subcategoryRepository.save(newSubcategory);
		SubcategoryResponse response = SubcategoryResponseMapper.INSTANCE.toPayload(updatedSubcategory);
		response.setCategoryId(existingCategory.getCategoryId());
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
	
	
	private static List<SpecificationName> getSpecificationNames(SubcategoryRequest request, Subcategory newSubcategory){
		List<SpecificationNameRequest> specNameList = request.getSpecificationNames();
		return specNameList.stream().map(spec ->{
			SpecificationName name = SpecificationNameRequestMapper.INSTANCE.toEntity(spec);
			name.setSubcategory(newSubcategory);
			return name;
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

		
		List<SubcategoryImage> subcategoryImages = getSubcategoryImages(request, subcategory);
		List<SpecificationName> specificationNames = getSpecificationNames(request, subcategory);
		subcategory.setSubcategoryImages(subcategoryImages);
		subcategory.setSpecificationNames(specificationNames);
		subcategory.setProducts(existingSubcategory.getProducts());
		
		Subcategory updateSubcategory = subcategoryRepository.save(subcategory);
		SubcategoryResponse response = SubcategoryResponseMapper.INSTANCE.toPayload(updateSubcategory);
		return response;
	}
	
	public List<SubcategoryResponse> getAllSubcategories(){
		List<Subcategory> subcategories = subcategoryRepository.findAll();
		
		List<SubcategoryResponse> subcategoryResponses = subcategories.stream().map(subcat ->{
			SubcategoryResponse response = SubcategoryResponseMapper.INSTANCE.toPayload(subcat);
			response.setCategoryId(subcat.getCategory().getCategoryId());
			return response;
		}).collect(Collectors.toList());
		
		return subcategoryResponses;
	}
}
