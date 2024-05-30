package com.billioncart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.billioncart.model.Category;
import com.billioncart.payload.CategoryDetailsResponse;

@Mapper
public interface CategoryDetailsResponseMapper {
	CategoryDetailsResponseMapper  INSTANCE = Mappers.getMapper(CategoryDetailsResponseMapper.class);
	
	CategoryDetailsResponse toPayload(Category category);
}
