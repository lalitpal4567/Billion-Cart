package com.billioncart.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "subcategory_table")
public class Subcategory {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long subcategoryId;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	@JsonIgnore
	private Category category;
		
	@OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SubcategoryImage> subcategoryImages;
	
	@OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Product> products;
	
	@OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<SpecificationName> specificationNames;
}
