package com.billioncart.service.serviceImpl;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.billioncart.exception.CartNotFoundException;
import com.billioncart.exception.NotEnoughStockException;
import com.billioncart.exception.ProductNotFoundException;
import com.billioncart.model.Account;
import com.billioncart.model.Cart;
import com.billioncart.model.CartItem;
import com.billioncart.model.Product;
import com.billioncart.repository.AccountRepository;
import com.billioncart.repository.CartItemRepository;
import com.billioncart.repository.CartRepository;
import com.billioncart.repository.ProductRepository;
import com.billioncart.service.CartService;

@Service	
public class CartServiceImpl implements CartService{
	private CartRepository cartRepository;
	private ProductRepository productRepository;
	private AccountRepository accountRepository;
	private CartItemRepository cartItemRepository;
	
	public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, AccountRepository accountRepository, CartItemRepository cartItemRepository) {
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.accountRepository = accountRepository;
		this.cartItemRepository = cartItemRepository;
	}
	
	public void addToCart(Long productId) {
		Product existingProduct =  productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
		
		String username = UserDetailsUtils.getAuthenticatedUsername();
		Account existingAccount = accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
		
		Cart existingCart = cartRepository.findById(existingAccount.getUser().getCart().getCartId()).orElseThrow(() -> new CartNotFoundException("Cart not found"));
		
		Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndProduct(existingCart, existingProduct);
		
		
		if(existingCartItem.isPresent()) {
			CartItem cartItem = existingCartItem.get();
			
			if(existingProduct.getQuantity() <= cartItem.getQuantity()) {
				throw new NotEnoughStockException("Quantity is insufficient.");
			}
			
			cartItem.setQuantity(cartItem.getQuantity() + 1);
			cartItemRepository.save(cartItem);
			existingCart.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
			cartRepository.save(existingCart);
			throw new RuntimeException("Product is already present");
		}
		
		// add new cartItem if it not present
		CartItem newCartItem = new CartItem();
		newCartItem.setCart(existingCart);
		newCartItem.setProduct(existingProduct);
		newCartItem.setQuantity(1);
		newCartItem.setIsSelectedForOrder(true);
		cartItemRepository.save(newCartItem);
		
		// update the Timestamp of the associated cart
		existingCart.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		existingCart.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		cartRepository.save(existingCart);
	}
}
