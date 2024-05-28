package com.billioncart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billioncart.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
