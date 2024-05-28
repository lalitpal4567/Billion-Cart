package com.billioncart.payload;

import java.util.List;

import com.billioncart.model.SubcategoryImage;

import lombok.Data;

@Data
public class SubcategoryResponse {
	private Long subcategoryId;
	private String name;
	private String description;
	private Long categoryId;
	private List<SubcategoryImage> imageUrls;
}
