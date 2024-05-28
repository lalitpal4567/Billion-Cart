package com.billioncart.payload;

import lombok.Data;

@Data
public class UserResponse {
	private String firstName;
	private String lastName;
	private String dob;
	private String email;
	private String profilePictureUrl;
}
