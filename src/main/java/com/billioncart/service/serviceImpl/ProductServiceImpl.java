package com.billioncart.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billioncart.exception.ProductNotFoundException;
import com.billioncart.exception.ResourceNotFoundException;
import com.billioncart.mapper.CategoryDetailsResponseMapper;
import com.billioncart.mapper.ProductImageRequestMapper;
import com.billioncart.mapper.ProductRequestMapper;
import com.billioncart.mapper.ProductResponseMapper;
import com.billioncart.mapper.SpecificationNameValueResponseMapper;
import com.billioncart.mapper.SpecificationValueRequestMapper;
import com.billioncart.model.Product;
import com.billioncart.model.ProductImage;
import com.billioncart.model.SpecificationName;
import com.billioncart.model.SpecificationValue;
import com.billioncart.model.Subcategory;
import com.billioncart.payload.CategoryDetailsResponse;
import com.billioncart.payload.ProductImageRequest;
import com.billioncart.payload.ProductRequest;
import com.billioncart.payload.ProductResponse;
import com.billioncart.payload.SpecificationResponse;
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
	private static SpecificationNameRepository specificationNameRepository;

	public ProductServiceImpl(ProductRepository productRepository, SubcategoryRepository subcategoryRepository,
			SpecificationValueRepository specificationValueRepository,
			SpecificationNameRepository specificationNameRepository) {
		this.productRepository = productRepository;
		this.subcategoryRepository = subcategoryRepository;
		this.specificationNameRepository = specificationNameRepository;
	}

	@Transactional
	public ProductResponse addProduct(ProductRequest request) {
		// fetch the existing subcategory by Id
		Subcategory existingSubcategory = subcategoryRepository.findById(request.getSubcategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));

		// map the ProductRequest to a Product entity
		Product newProduct = ProductRequestMapper.INSTANCE.toEntity(request);
		// set the subcategory for new product
		newProduct.setSubcategory(existingSubcategory);

		// get the product images from the request and set the product reference for
		// each image
		List<ProductImage> productImages = getProductImages(request, newProduct);

		// get the specification values from the request and set the product reference
		// for each value
		List<SpecificationValue> specificationValues = getSpecificationValues(request, newProduct);

		// set the product images in the new product
		newProduct.setProductImages(productImages);
		// set the specification values in the new product
		newProduct.setSpecificationValues(specificationValues);
		// save the new product to the repository
		Product createdProduct = productRepository.save(newProduct);

		// get the specification values of the created product
		List<SpecificationResponse> specificationResponses = getSpecificationResponses(createdProduct);

		// map the created product to its response payload
		ProductResponse productResponse = ProductResponseMapper.INSTANCE.toPayload(createdProduct);
		// set the specification name-value responses in the product response
		productResponse.setSpecifications(specificationResponses);
		// map the subcategory's category to its response payload
		CategoryDetailsResponse categoryResponse = CategoryDetailsResponseMapper.INSTANCE
				.toPayload(existingSubcategory.getCategory());
		// set the category response in the product response
		productResponse.setCategory(categoryResponse);
		// return the complete product response
		return productResponse;
	}

	// helper method to get product images from the request and set the product
	// reference
	private static List<ProductImage> getProductImages(ProductRequest request, Product newProduct) {
		List<ProductImageRequest> imageList = request.getProductImages();
		return imageList.stream().map(img -> {
			// map the ProductImageRequest to a ProductImage entity
			ProductImage image = ProductImageRequestMapper.INSTANCE.toEntity(img);
			// set the product reference in the image
			image.setProduct(newProduct);
			return image;
		}).collect(Collectors.toList());
	}

	private static List<SpecificationResponse> getSpecificationResponses(Product createdProduct) {
		List<SpecificationValue> createdSpecificationValues = createdProduct.getSpecificationValues();
		return createdSpecificationValues.stream().map(spec -> {
			SpecificationResponse specificationResponse = SpecificationNameValueResponseMapper.INSTANCE.toPayload(spec);
			// set the specification name in the response
			specificationResponse.setName(spec.getSpecificationName().getName());
			return specificationResponse;
		}).collect(Collectors.toList());
	}

	// helper method to get specification values from the request and set the
	// product and specification name references
	private static List<SpecificationValue> getSpecificationValues(ProductRequest request, Product newProduct) {

		List<SpecificationValueRequest> valueList = request.getSpecificationValues();

		return valueList.stream().map(value -> {
			// map the SpecificationValueRequest to a SpecificationValue entity
			SpecificationValue values = SpecificationValueRequestMapper.INSTANCE.toEntity(value);
			// fetch the specification name by Id and set it in the specification value

			SpecificationName sname = ProductServiceImpl.specificationNameRepository.findById(value.getNameId())
					.orElseThrow(() -> new ResourceNotFoundException("Specification name not found"));
			// set the product reference in the specification value
			values.setProduct(newProduct);
			// set the specification name reference in the specification value
			values.setSpecificationName(sname);
			return values;
		}).collect(Collectors.toList());
	}

	public void removeProduct(Long productId) {
		Product existingProduct = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));

		productRepository.deleteById(productId);
	}

	public Product getProductById(Long productId) {
		Product existingProduct = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));
		return existingProduct;
	}

	@Transactional
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
}
