package com.billioncart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billioncart.model.SpecificationName;
import com.billioncart.model.Subcategory;

public interface SpecificationNameRepository extends JpaRepository<SpecificationName, Long>{
	List<SpecificationName> findAllBySubcategory(Subcategory subcategory);
}
