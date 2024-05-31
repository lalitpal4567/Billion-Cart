package com.billioncart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
@Table(name = "spec_name_table")
public class SpecificationName {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long nameId;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "subcategory_id")
	@JsonIgnore
	private Subcategory subcategory;
}
