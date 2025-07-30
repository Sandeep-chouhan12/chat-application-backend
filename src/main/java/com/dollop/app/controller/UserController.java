package com.dollop.app.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.app.module.OnlineUserTracker;
import com.dollop.app.request.UserUpdateRequest;
import com.dollop.app.response.ApiResponse;
import com.dollop.app.service.IUserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {

	@Autowired
	private IUserService userService;
    @Autowired
    private OnlineUserTracker onlineUserTracker;
	
    @PutMapping("/update-user")
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UserUpdateRequest updatedUser ) {
        return new  ResponseEntity<ApiResponse>(userService.updateUser(updatedUser),HttpStatus.OK);
    }
    
    @GetMapping("/get-current-user")
    public ResponseEntity<ApiResponse> getCurrentUser() {
        return new  ResponseEntity<ApiResponse>(userService.currentUserById(),HttpStatus.OK);
    }
	
    @GetMapping("/get-all-user")
    public ResponseEntity<ApiResponse> getAllUser() {
        return new  ResponseEntity<ApiResponse>(userService.getAllUsers(),HttpStatus.OK);
    }
  
//    
//    @DeleteMapping("/delete-user")
//    public ResponseEntity<ApiResponse> deleteUser(@NotBlank(message ="is not be blank or null" )  String id) {
//        return new  ResponseEntity<ApiResponse>(userService.deleteUser(id),HttpStatus.OK);
//    }
    
    
    @GetMapping("/getOnlineUsers")
    public ResponseEntity<Set<String>> getOnlineUsers() {
        return ResponseEntity.ok(onlineUserTracker.getOnlineUsers());
    }
}
