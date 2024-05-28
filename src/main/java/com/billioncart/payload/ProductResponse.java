package com.billioncart.payload;

import com.billioncart.model.Subcategory;

import lombok.Data;

@Data
public class ProductResponse {
	private Long productId;
	private String name;
	private String description;
	private int quantity;
	private float price;
	private Subcategory subcategoryId;
//	private Specification
}
