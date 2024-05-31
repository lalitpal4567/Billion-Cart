package com.billioncart.service.serviceImpl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.billioncart.mapper.UserRequestMapper;
import com.billioncart.mapper.UserResponseMapper;
import com.billioncart.model.Account;
import com.billioncart.model.User;
import com.billioncart.payload.UserRequest;
import com.billioncart.payload.UserResponse;
import com.billioncart.repository.AccountRepository;
import com.billioncart.repository.UserRepository;
import com.billioncart.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	private UserRepository userRepository;
	private AccountRepository accountRepository;
	
	public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
	}
	
	public UserResponse updateProfile(UserRequest request) {
		// Get the authenticated user's username
		String username = UserDetailsUtils.getAuthenticatedUsername();        
        // find the account by username
        Account existingAccount = accountRepository.findByUsername(username)
        		.orElseThrow(() -> new UsernameNotFoundException("Account not found"));
        
        // find user by user id
        User existingUser = userRepository.findById(existingAccount.getUser().getUserId())
        		.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        // convert request payload to User entity
        User user = UserRequestMapper.INSTANCE.toEntity(request);
        
     // Preserve important fields
        user.setUserId(existingUser.getUserId());
        user.setCart(existingUser.getCart());
        user.setWishlist(existingUser.getWishlist());
        
        // save user to user repository
       User updatedUser = userRepository.save(user);
       
       // convert saved user entity back to userResponse payload
        return UserResponseMapper.INSTANCE.toPayload(user);
	}
}
