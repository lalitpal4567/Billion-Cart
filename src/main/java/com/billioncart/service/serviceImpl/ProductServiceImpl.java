package com.billioncart.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billioncart.exception.ProductNotFoundException;
import com.billioncart.exception.ResourceNotFoundException;
import com.billioncart.exception.SubcategoryNotFoundException;
import com.billioncart.mapper.CategoryDetailsResponseMapper;
import com.billioncart.mapper.ProductImageRequestMapper;
import com.billioncart.mapper.ProductImageResponseMapper;
import com.billioncart.mapper.ProductRequestMapper;
import com.billioncart.mapper.ProductResponseMapper;
import com.billioncart.mapper.SpecificationNameValueResponseMapper;
import com.billioncart.mapper.SpecificationValueRequestMapper;
import com.billioncart.mapper.SubcategoryDetailsResponseMapper;
import com.billioncart.model.Product;
import com.billioncart.model.ProductImage;
import com.billioncart.model.SpecificationName;
import com.billioncart.model.SpecificationValue;
import com.billioncart.model.Subcategory;
import com.billioncart.payload.CategoryDetailsResponse;
import com.billioncart.payload.ProductImageRequest;
import com.billioncart.payload.ProductImageResponse;
import com.billioncart.payload.ProductRequest;
import com.billioncart.payload.ProductResponse;
import com.billioncart.payload.SpecificationResponse;
import com.billioncart.payload.SpecificationValueRequest;
import com.billioncart.payload.SubcategoryDetailsResponse;
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
		ProductServiceImpl.specificationNameRepository = specificationNameRepository;
	}

	@Override
	@Transactional
	public ProductResponse addProduct(Long subcategoryId, ProductRequest request) {
		Subcategory existingSubcategory = subcategoryRepository.findById(subcategoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));

		Product newProduct = ProductRequestMapper.INSTANCE.toEntity(request);
		newProduct.setSubcategory(existingSubcategory);

		List<ProductImage> productImages = getProductImages(request, newProduct);
		List<SpecificationValue> specificationValues = getSpecificationValues(request, newProduct);

		newProduct.setProductImages(productImages);
		newProduct.setSpecificationValues(specificationValues);
		Product createdProduct = productRepository.save(newProduct);
		
		return getProductResponse(createdProduct);
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

	@Override
	public void removeProduct(Long productId) {
		Product existingProduct = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));

		productRepository.deleteById(productId);
	}

	private static ProductResponse getProductResponse(Product product) {
		CategoryDetailsResponse categoryDetailsResponse = CategoryDetailsResponseMapper.INSTANCE
				.toPayload(product.getSubcategory().getCategory());
		SubcategoryDetailsResponse subcategoryDetailsResponse = SubcategoryDetailsResponseMapper.INSTANCE
				.toPayload(product.getSubcategory());

		ProductResponse productResponse = ProductResponseMapper.INSTANCE.toPayload(product);
		productResponse.setProductImages(getImageResponse(product));
		productResponse.setSpecifications(getSpecificationResponses(product));
		productResponse.setCategory(categoryDetailsResponse);
		productResponse.setSubcategory(subcategoryDetailsResponse);

		return productResponse;
	}

	private static List<ProductImageResponse> getImageResponse(Product product) {
		List<ProductImage> productImages = product.getProductImages();
		List<ProductImageResponse> productImageResponses = productImages.stream().map(img -> {
			ProductImageResponse productImageResponse = ProductImageResponseMapper.INSTANCE.toPayload(img);
			return productImageResponse;
		}).collect(Collectors.toList());

		return productImageResponses;
	}

	@Override
	public ProductResponse getProductById(Long productId) {
		Product existingProduct = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));

		return getProductResponse(existingProduct);
	}

	@Override
	@Transactional
	public Page<ProductResponse> getProductBySubcategoryId(Long subcategoryId, Integer page, Integer size) {
		Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
				.orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found"));

		Page<Product> productPage = productRepository.findAllBySubcategory(PageRequest.of(page, size), subcategory);

		Page<ProductResponse> productResponsePage = productPage.map(p -> getProductResponse(p));
		return productResponsePage;
	}

	@Override
	@Transactional
	public Page<ProductResponse> getAllProducts(Integer page, Integer size) {
		Page<Product> productsPage = productRepository.findAll(PageRequest.of(page, size));

		Page<ProductResponse> productsResponsePage = productsPage.map(product -> getProductResponse(product));
		return productsResponsePage;
	}
}
