package com.dollop.app.serviceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dollop.app.constant.MessageConstants;
import com.dollop.app.entity.User;
import com.dollop.app.exceptions.ResourceNotFound;
import com.dollop.app.repository.IUserRepository;
import com.dollop.app.request.LoginRequest;
import com.dollop.app.request.UserRequest;
import com.dollop.app.request.UserUpdateRequest;
import com.dollop.app.response.ApiResponse;
import com.dollop.app.response.UserResponse;
import com.dollop.app.service.IUserService;
import com.dollop.app.utils.AppUtils;
import com.dollop.app.utils.JwtUtils;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService {

	private IUserRepository userRepository;	
	private BCryptPasswordEncoder passwordEncoder;
	private JwtUtils jwtUtils;
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AppUtils appUtils;
	
	
	public UserServiceImpl(IUserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtils jwtUtils,
		  	@Lazy AuthenticationManager authenticationManager) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public ApiResponse createUser(UserRequest userRequest) {
		
	    if(userRepository.existsByEmail(userRequest.getEmail())||userRepository.existsByUserName(userRequest.getUserName()))
	    {
	    	throw new ResourceNotFound(MessageConstants.ALREADY_EXISTS_MESSAGE);
	    }
	   
	    User user = User.builder()
	    		    .name(userRequest.getName())
	    		    .email(userRequest.getEmail())
	    		    .userName(userRequest.getUserName())
	    		    .password(passwordEncoder.encode(userRequest.getPassword()))
	    		    .profileImageUrl("https://res.cloudinary.com/dvhloff4c/image/upload/v1742537457/free-user-icon-3296-thumb_qteroi.png")
	    		    .build();
	    
	     User savedUser = userRepository.save(user);
	    
		return ApiResponse.builder().message("user registered successfully").build();
	}
	
	@Override
	public ApiResponse userLogin(LoginRequest loginRequest) {
		Authentication auth = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	            		loginRequest.getEmail(),
	            		loginRequest.getPassword()
	            )
	        );

	        // Ab direct authenticated user details le lo
	        UserDetails userDetails = (UserDetails) auth.getPrincipal();

	        String token = jwtUtils.generateToken(userDetails.getUsername(),findByEmail(userDetails.getUsername()).getId());
		return ApiResponse.builder().response(Map.of("token" , token)).message("User Login Successfully !").build();

	}


	@Override
	public ApiResponse currentUserById() {
		User user = findCurrentUserById();
		return ApiResponse.builder().message("User Profile Fetched").response(Map.of("user",userToUserResponse(user))).build();
	}

	@Override
	public ApiResponse getAllUsers() {
		User currentUserById = findCurrentUserById();
		List<User> allUsers = userRepository.findByIdNot(currentUserById.getId());
		if(allUsers.isEmpty())
			throw new ResourceNotFound(MessageConstants.NOT_FOUND_MESSAGE);
		
		return ApiResponse.builder().message("All User").response(Map.of("users",userToUserResponse(allUsers))).build();
	}

	@Override
	public ApiResponse updateUser(UserUpdateRequest updatedUser) {
	
		User user = findCurrentUserById();
		
		if(userRepository.existsByEmailAndIdNot(updatedUser.getEmail(),user.getId()))
		{
			throw new ResourceNotFound(MessageConstants.ALREADY_EXISTS_MESSAGE);
		}
		user.setName(updatedUser.getName()==null?user.getName():updatedUser.getName());
		user.setEmail(updatedUser.getEmail()==null?user.getEmail():updatedUser.getEmail());
	
		User save = userRepository.save(user);
		return ApiResponse.builder().message("User Updated Successfully !").build();
	}
	
	@Override
	public User findCurrentUserById() {
		String id = appUtils.getIdFromToken();
		User user = userRepository.findById(id)
		         .orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return user;
	}
	
	@Override
	public User findUserById(String id) {
		User user = userRepository.findById(id)
		         .orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return user;
	}
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    User user =findByEmail(username);
	    return new org.springframework.security.core.userdetails.User(
	            user.getEmail(),
	            user.getPassword(),
	            Collections.emptyList() // No roles or authorities
	    );
	}
	
	public User findByEmail(String email)
	{
		User user = userRepository.findByEmail(email)
         .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		return user;
	}
	
	@Override
	public ApiResponse deleteUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
		
	public List<UserResponse> userToUserResponse(List<User> user){
		return user.stream().map(this::userToUserResponse).toList();
	}
	
	public UserResponse userToUserResponse(User user){
		return UserResponse.builder()
	            .id(user.getId())
	            .name(user.getName())
	            .userName(user.getUserName())
	            .email(user.getEmail())
	            .profileImageUrl(user.getProfileImageUrl())
	            .status(user.getStatustype())
	            .lastSeen(Timestamp.valueOf(user.getLastSeen()))
	            .build();
	}

	@Override
	public UserResponse updateUserLastSeen(String email) {
		User user = findByEmail(email);
		user.setLastSeen(LocalDateTime.now());
		User save = userRepository.save(user);
		return userToUserResponse(save);
	}

}
