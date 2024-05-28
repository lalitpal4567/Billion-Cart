package com.billioncart.model;

import java.util.List;

import com.billioncart.audit.UserDateAudit;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "wishlist_table")
public class Wishlist extends UserDateAudit{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long wishlistId;
	
	@OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL)
	private List<WishlistItem> wishlistItems;

}
