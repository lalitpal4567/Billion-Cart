package com.billioncart.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billioncart.exception.ProductNotFoundException;
import com.billioncart.exception.ResourceNotFoundException;
import com.billioncart.mapper.ProductImageRequestMapper;
import com.billioncart.mapper.ProductRequestMapper;
import com.billioncart.mapper.SpecificationValueRequestMapper;
import com.billioncart.model.Product;
import com.billioncart.model.ProductImage;
import com.billioncart.model.SpecificationName;
import com.billioncart.model.SpecificationValue;
import com.billioncart.model.Subcategory;
import com.billioncart.payload.ProductImageRequest;
import com.billioncart.payload.ProductRequest;
import com.billioncart.payload.SpecificationValueRequest;
import com.billioncart.repository.ProductRepository;
import com.billioncart.repository.SpecificationNameRepository;
import com.billioncart.repository.SpecificationValueRepository;
import com.billioncart.repository.SubcategoryRepository;
import com.billioncart.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	private ProductRepository productRepository;
	private SubcategoryRepository subcategoryRepository;
	private SpecificationValueRepository specificationValueRepository;
	private static SpecificationNameRepository specificationNameRepository;

	public ProductServiceImpl(ProductRepository productRepository, SubcategoryRepository subcategoryRepository,
			SpecificationValueRepository specificationValueRepository,
			SpecificationNameRepository specificationNameRepository) {
		this.productRepository = productRepository;
		this.subcategoryRepository = subcategoryRepository;
		this.specificationValueRepository = specificationValueRepository;
		this.specificationNameRepository = specificationNameRepository;
	}

	@Transactional
	public Product addProduct(ProductRequest request) {
		Subcategory existingSubcategory = subcategoryRepository.findById(request.getSubcategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));

		Product newProduct = ProductRequestMapper.INSTANCE.toEntity(request);
		newProduct.setSubcategory(existingSubcategory);
		
		List<ProductImage> imageLists = getProductImages(request, newProduct);
		List<SpecificationValue> valueLists = getSpecificationValues(request, newProduct);
		
		newProduct.setImageUrls(imageLists);
		newProduct.setSpecValues(valueLists);
		Product createdProduct = productRepository.save(newProduct);
		
		return createdProduct;
	}
	
	private static List<ProductImage> getProductImages(ProductRequest request, Product newProduct){
		List<ProductImageRequest> imageLists = request.getImageUrls();

		return imageLists.stream().map(url -> {
			ProductImage image = ProductImageRequestMapper.INSTANCE.toEntity(url);
			image.setProduct(newProduct);
			return image;
		}).collect(Collectors.toList());
	}
	
	private static List<SpecificationValue> getSpecificationValues(ProductRequest request, Product newProduct){
		List<SpecificationValueRequest> valueList = request.getSpecValues();

	        return valueList.stream().map(value ->{
			SpecificationValue values = SpecificationValueRequestMapper.INSTANCE.toEntity(value);
			SpecificationName sname = specificationNameRepository.findById(value.getSpecNameId()).orElseThrow(() -> new ResourceNotFoundException("Specification name not found"));
			values.setProduct(newProduct);
			values.setSpecName(sname);
			return values;
		}).collect(Collectors.toList());	
	}
	
	public void removeProduct(Long productId) {
		Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
		
		productRepository.deleteById(productId);
	}
	
	public Product getProductById(Long productId) {
		Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
		return existingProduct;
	}
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
}
