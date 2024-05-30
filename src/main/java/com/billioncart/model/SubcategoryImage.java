package com.billioncart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "sub_img_url_table")
public class SubcategoryImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imageUrlId;
	
	private String imageUrl;
	private String description;
	
	@Column(nullable = false)
	private String altText;
	
	@ManyToOne
	@JoinColumn(name = "subcategoryId")
	@JsonIgnore
	private Subcategory subcategory;
}
