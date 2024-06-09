package com.billioncart.model;

import java.util.List;
import java.util.Set;

import com.billioncart.repository.SpecificationValueRepository;
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
@Table(name = "product_table")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column(nullable = false)
	private float currentPrice;

	@Column(nullable = false)
	private float previousPrice;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private String details;
	
	private String model;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<CartItem> cartItems;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Review> reviews;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SpecificationValue> specificationValues;
	
	@ManyToOne
	@JoinColumn(name = "subcategory_id")
	private Subcategory subcategory;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductImage> productImages;
}
