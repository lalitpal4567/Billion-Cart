package com.billioncart.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.billioncart.service.WishlistService;

@RestController
@RequestMapping("/api/v1/user/wishlist")
public class WishlistController {
	private WishlistService wishlistService;
	
	public WishlistController(WishlistService wishlistService) {
		this.wishlistService = wishlistService;
	}
	
	@PostMapping("/add-product/{id}")
	public ResponseEntity<Map<String, Object>> addToCart(@PathVariable(name = "id") Long productId) {
		Map<String, Object> res = new LinkedHashMap<>();
		System.out.println("hi");

		try {
			wishlistService.addToWishlist(productId);
			res.put("message", "Wishlist added successfully");
			return ResponseEntity.status(HttpStatus.CREATED).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
}
