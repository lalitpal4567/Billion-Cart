package com.billioncart.payload;

import java.util.List;

import com.billioncart.model.ProductImage;

import lombok.Data;

@Data
public class ProductRequest {
	private String name;
	private String description;
	private Long subcategoryId;
	private int quantity;
	private float price;
	private List<SpecificationValueRequest> specValues;
	private List<ProductImageRequest> imageUrls;
}
