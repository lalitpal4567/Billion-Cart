package com.billioncart.payload;

import lombok.Data;

@Data
public class SubcategoryImageRequest {
	private String imageUrl;
	private String description;
	private String altText;
}
