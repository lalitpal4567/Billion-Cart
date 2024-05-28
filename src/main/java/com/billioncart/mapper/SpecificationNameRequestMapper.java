package com.billioncart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.billioncart.model.SpecificationName;
import com.billioncart.payload.SpecificationNameRequest;

@Mapper
public interface SpecificationNameRequestMapper {
	SpecificationNameRequestMapper INSTANCE = Mappers.getMapper(SpecificationNameRequestMapper.class);
	
	SpecificationName toEntity(SpecificationNameRequest request);
}
