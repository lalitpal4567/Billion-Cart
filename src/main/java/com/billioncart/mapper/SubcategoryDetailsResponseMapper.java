package com.billioncart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.billioncart.model.Subcategory;
import com.billioncart.payload.SubcategoryDetailsResponse;

@Mapper
public interface SubcategoryDetailsResponseMapper {
	SubcategoryDetailsResponseMapper INSTANCE	= Mappers.getMapper(SubcategoryDetailsResponseMapper.class);
	
	SubcategoryDetailsResponse toPayload(Subcategory subcategory);
}
