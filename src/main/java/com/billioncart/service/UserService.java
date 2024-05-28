package com.billioncart.service;

import com.billioncart.payload.UserRequest;
import com.billioncart.payload.UserResponse;

public interface UserService {
	UserResponse updateProfile(UserRequest request);
}
