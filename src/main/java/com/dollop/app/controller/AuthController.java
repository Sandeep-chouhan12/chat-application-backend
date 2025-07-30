package com.dollop.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.app.request.LoginRequest;
import com.dollop.app.request.UserRequest;
import com.dollop.app.response.ApiResponse;
import com.dollop.app.service.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

	@Autowired
	private IUserService userService;
	@PostMapping("/register")
	public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserRequest userRequest)
	{
		System.err.println("=== > register");
		return new ResponseEntity<ApiResponse>(userService.createUser(userRequest),HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest)
	{
		return new ResponseEntity<ApiResponse>(userService.userLogin(loginRequest),HttpStatus.OK);
	}
	
}
