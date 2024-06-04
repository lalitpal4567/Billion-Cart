package com.billioncart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billioncart.model.Product;
import com.billioncart.model.Wishlist;
import com.billioncart.model.WishlistItem;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long>{
	Optional<WishlistItem> findWishlistItemByWishlistAndProduct(Wishlist wishlist, Product product);
}
