package com.billioncart.payload;

import java.util.List;

import com.billioncart.model.Category;
import com.billioncart.model.SubcategoryImage;

import lombok.Data;

@Data
public class SubcategoryResponse {
	private Long subcategoryId;
	private String name;
	private String description;
	private CategoryResponse category;
	private List<SubcategoryImage> subcategoryImages;
}
