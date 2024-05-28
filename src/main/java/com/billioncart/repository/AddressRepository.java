package com.billioncart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billioncart.model.Address;
import com.billioncart.model.User;

public interface AddressRepository extends JpaRepository<Address, Long>{
	void removeByUserAndAddressId(User user, Long addressId);
//	void removeByUserIdAndAddressId(Long userId, Long addressId);
//	void removeByAddressIdAndUserId(Long userId, Long addressId);
//	void removeByUserUserIdAndAddressId(Long userId, Long addressId);


}
