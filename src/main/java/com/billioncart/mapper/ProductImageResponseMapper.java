package com.billioncart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.billioncart.model.ProductImage;
import com.billioncart.payload.ProductImageResponse;

@Mapper
public interface ProductImageResponseMapper {
	ProductImageResponseMapper INSTANCE = Mappers.getMapper(ProductImageResponseMapper.class);
	
	ProductImageResponse toPayload(ProductImage productImage);
}
