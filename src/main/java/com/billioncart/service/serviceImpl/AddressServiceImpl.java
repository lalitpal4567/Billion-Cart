package com.billioncart.service.serviceImpl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.billioncart.mapper.AddressRequestMapper;
import com.billioncart.mapper.AddressResponseMapper;
import com.billioncart.model.Account;
import com.billioncart.model.Address;
import com.billioncart.model.User;
import com.billioncart.payload.AddressRequest;
import com.billioncart.payload.AddressResponse;
import com.billioncart.repository.AccountRepository;
import com.billioncart.repository.AddressRepository;
import com.billioncart.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService{
	private AddressRepository addressRepository;
	private AccountRepository accountRepository;
	
	public AddressServiceImpl(AddressRepository addressRepository, AccountRepository accountRepository) {
		this.addressRepository = addressRepository;
		this.accountRepository = accountRepository;
	}
	
	public AddressResponse addAddress(AddressRequest request) {
		String username = UserDetailsUtils.getAuthenticatedUsername();
		
		   // find the account by username
        Account existingAccount = accountRepository.findByUsername(username)
        		.orElseThrow(() -> new UsernameNotFoundException("Account not found"));        
        
        Address newAddress = AddressRequestMapper.INSTANCE.toEntity(request);
        newAddress.setUser(existingAccount.getUser());
        newAddress.setIsDefaultAddress(false);
        Address createdAddress = addressRepository.save(newAddress);
        
        return AddressResponseMapper.INSTANCE.toPayload(createdAddress);
	}
	
	public void removeAddress(Long addressId) {
		String username = UserDetailsUtils.getAuthenticatedUsername();
		
		 // find the account by username
        Account existingAccount = accountRepository.findByUsername(username)
        		.orElseThrow(() -> new UsernameNotFoundException("Account not found"));
        
        addressRepository.removeByUserAndAddressId(existingAccount.getUser(), addressId);
	}
}
