package com.billioncart.payload;


import java.util.List;

import lombok.Data;

@Data
public class ProductResponse {
	private Long productId;
	private String name;
	private String description;
	private String details;
	private int quantity;
	private float currentPrice;
	private float previousPrice;
	private SubcategoryDetailsResponse subcategory;
	private CategoryDetailsResponse category;
	private List<ProductImageResponse> productImages;
	private List<SpecificationResponse> specifications;
}


