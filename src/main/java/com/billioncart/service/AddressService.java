package com.billioncart.service;

import com.billioncart.payload.AddressRequest;
import com.billioncart.payload.AddressResponse;

public interface AddressService {
	AddressResponse addAddress(AddressRequest request);
	void removeAddress(Long addressId);
}
