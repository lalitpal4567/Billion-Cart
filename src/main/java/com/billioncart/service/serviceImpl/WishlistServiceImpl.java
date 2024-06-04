package com.billioncart.service.serviceImpl;

import java.sql.Timestamp;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.billioncart.exception.ProductNotFoundException;
import com.billioncart.exception.WishlistNotFoundException;
import com.billioncart.model.Account;
import com.billioncart.model.Product;
import com.billioncart.model.Wishlist;
import com.billioncart.model.WishlistItem;
import com.billioncart.repository.AccountRepository;
import com.billioncart.repository.ProductRepository;
import com.billioncart.repository.WishlistItemRepository;
import com.billioncart.repository.WishlistRepository;
import com.billioncart.service.WishlistService;

@Service
public class WishlistServiceImpl implements WishlistService{
	private WishlistRepository wishlistRepository;
	private ProductRepository productRepository;
	private AccountRepository accountRepository;
	private WishlistItemRepository wishlistItemRepository;
	
	public WishlistServiceImpl(WishlistRepository wishlistRepository, ProductRepository productRepository, AccountRepository accountRepository, WishlistItemRepository wishlistItemRepository) {
		this.wishlistRepository = wishlistRepository;
		this.productRepository = productRepository;
		this.accountRepository = accountRepository;
		this.wishlistItemRepository = wishlistItemRepository;
	}
	
	@Override
	public void addToWishlist(Long productId) {
		Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
		
		String username = UserDetailsUtils.getAuthenticatedUsername();
		
		Account existingAccount = accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
		Wishlist existingWishlist = wishlistRepository.findById(existingAccount.getUser().getWishlist().getWishlistId()).orElseThrow(() -> new WishlistNotFoundException("Wishlist not found"));
		
		Optional<WishlistItem> wishlistItem = wishlistItemRepository.findWishlistItemByWishlistAndProduct(existingWishlist, existingProduct);
		
		if(wishlistItem.isEmpty()) {
			WishlistItem newWishlistItem = new WishlistItem();
			newWishlistItem.setProduct(existingProduct);
			newWishlistItem.setWishlist(existingWishlist);
			wishlistItemRepository.save(newWishlistItem);
			
			existingWishlist.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
			wishlistRepository.save(existingWishlist);
		}else {
			throw new RuntimeException("Product already exists.");
		}
	}
}
