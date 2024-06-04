package com.billioncart.exception;

public class AddressNotFoundException extends RuntimeException{
	public AddressNotFoundException() {}
	
	public AddressNotFoundException(String message) {
		super(message);
	}
}
