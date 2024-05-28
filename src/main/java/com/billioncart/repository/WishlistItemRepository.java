package com.billioncart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billioncart.model.WishlistItem;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long>{

}
