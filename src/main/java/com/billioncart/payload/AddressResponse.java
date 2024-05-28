package com.billioncart.payload;

import lombok.Data;

@Data
public class AddressResponse {
	private String fullName;
	private String mobileNo;
	private int street;
	private String city;
	private String state;
	private String country;
	private String pincode;
	private Boolean isDefaultAddress;
}
