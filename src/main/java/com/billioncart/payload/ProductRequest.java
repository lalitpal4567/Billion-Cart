package com.billioncart.payload;

import java.util.List;

import com.billioncart.model.ProductImage;

import lombok.Data;

@Data
public class ProductRequest {
	private String name;
	private String description;
	private String details;
	private float currentPrice;
	private float previousPrice;
	private int quantity;
	private List<SpecificationValueRequest> specificationValues;
	private List<ProductImageRequest> productImages;
}
