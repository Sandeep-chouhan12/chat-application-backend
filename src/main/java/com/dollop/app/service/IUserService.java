package com.dollop.app.service;

import java.util.List;

import com.dollop.app.entity.User;
import com.dollop.app.request.LoginRequest;
import com.dollop.app.request.UserRequest;
import com.dollop.app.request.UserUpdateRequest;
import com.dollop.app.response.ApiResponse;
import com.dollop.app.response.UserResponse;

public interface IUserService {

	ApiResponse createUser(UserRequest userRequest);

	ApiResponse userLogin(LoginRequest loginRequest);
	
	ApiResponse currentUserById();

	ApiResponse getAllUsers();

	ApiResponse updateUser(UserUpdateRequest updatedUser);

	ApiResponse deleteUser(String userId);
	
	//only for backend use
	User findCurrentUserById();
	
    User findUserById(String id);
   
    UserResponse userToUserResponse(User user);
    
    List<UserResponse> userToUserResponse(List<User> user);

    public User findByEmail(String email);
//	List<User> searchUsers(String keyword);

	UserResponse updateUserLastSeen(String email);
}
