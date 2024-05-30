package com.billioncart.model;

import java.util.List;

import org.hibernate.mapping.Constraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "spec_value_table", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "spec_name_id"}))
public class SpecificationValue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long valueId;
	
	private String value;
	
	@ManyToOne
	@JoinColumn(name = "product_Id")
	@JsonIgnore
	private Product product;
	
	@ManyToOne
    @JoinColumn(name = "spec_name_id")
	@JsonIgnore
    private SpecificationName specificationName;
}
