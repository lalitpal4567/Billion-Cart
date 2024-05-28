package com.billioncart.payload;

import java.util.List;

import lombok.Data;

@Data
public class SubcategoryRequest {
	private String name;
	private String description;
	private Long categoryId;
	private List<SubcategoryImageRequest> imageUrls;
	private List<SpecificationNameRequest> specNames;
}
