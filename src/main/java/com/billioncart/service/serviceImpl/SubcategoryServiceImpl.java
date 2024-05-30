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

		List<SubcategoryImageRequest> lists = request.getSubcategoryImages();
		List<SubcategoryImage> images = lists.stream().map(url -> {
			SubcategoryImage image = SubcategoryImageRequestMapper.INSTANCE.toEntity(url);
			image.setSubcategory(newSubcategory);
			return image;
		}).collect(Collectors.toList());

		List<SpecificationNameRequest> specNameList = request.getSpecificationNames();
		List<SpecificationName> specs = specNameList.stream().map(spec ->{
			SpecificationName name = SpecificationNameRequestMapper.INSTANCE.toEntity(spec);
			name.setSubcategory(newSubcategory);
			return name;
		}).collect(Collectors.toList());
		
		
	    newSubcategory.setImageUrls(images);
	    newSubcategory.setSpecificationNames(specs);
	    
	    
		Subcategory updatedSubcategory = subcategoryRepository.save(newSubcategory);
		SubcategoryResponse response = SubcategoryResponseMapper.INSTANCE.toPayload(updatedSubcategory);
		response.setCategoryId(existingCategory.getCategoryId());
		return response;
	}
	
	
	@Override
	public void removeSubcategoryById(Long subcategoryId) {
		Subcategory existingSubcategory = subcategoryRepository.findById(subcategoryId).orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found"));
		subcategoryRepository.deleteById(subcategoryId);
	}
	
	
}
