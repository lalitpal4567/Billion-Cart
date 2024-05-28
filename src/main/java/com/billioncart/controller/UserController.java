package com.billioncart.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.billioncart.model.Role;
import com.billioncart.model.User;
import com.billioncart.payload.UserRequest;
import com.billioncart.payload.UserResponse;
import com.billioncart.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/update-profile")
	public ResponseEntity<Map<String, Object>> addRole(@RequestBody  UserRequest request) {
		Map<String, Object> res = new LinkedHashMap<>();

		try {
			UserResponse response = userService.updateProfile(request);
			res.put("message", "User updated successfully");
			res.put("User", response);
			return ResponseEntity.status(HttpStatus.CREATED).body(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
}
