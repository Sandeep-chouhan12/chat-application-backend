package com.dollop.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.app.constant.MessageConstants;
import com.dollop.app.constant.ValidationConstants;
import com.dollop.app.request.AddMemberRequest;
import com.dollop.app.request.ChatRoomRequest;
import com.dollop.app.response.ApiResponse;
import com.dollop.app.service.IChatRoomService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/chat-room")
@Validated
public class ChatRoomController {
	
	@Autowired
	private IChatRoomService chatRoomService;

	
	@PostMapping("/create-room")
	public ResponseEntity<ApiResponse> createRoom(@Valid @RequestBody ChatRoomRequest roomRequest) {
		
		return new ResponseEntity<ApiResponse>(chatRoomService.createRoom(roomRequest),HttpStatus.CREATED);
	}
	
	@GetMapping("/get-room-by-id")
	public ResponseEntity<ApiResponse> getRoomById(@RequestParam
			@NotBlank(message = MessageConstants.Id_NOT_NULL)
            @Pattern(regexp = ValidationConstants.ID_REGEX, message =MessageConstants.INVALID_UUID)
	    	String roomId) {
		System.err.println("get room by id called .......");
		return new ResponseEntity<ApiResponse>(chatRoomService.getRoomById(roomId),HttpStatus.OK);
	}
	
	@GetMapping("/get-all-rooms")
	public ResponseEntity<ApiResponse> getAllRoom() {
		System.err.println(" get all room called .....................");
		return new ResponseEntity<ApiResponse>(chatRoomService.getAllRooms(),HttpStatus.OK);
	}
	
	@PostMapping("/add-room-member-in-group")
	public ResponseEntity<ApiResponse> addRoomMember(@Valid @RequestBody AddMemberRequest addMemberRequest) {
		System.err.println("add room member  ==== > " +addMemberRequest);
		return new ResponseEntity<ApiResponse>(chatRoomService.addRoomMember(addMemberRequest),HttpStatus.OK);
	}
	
	@DeleteMapping("/remove-room-member")
	public ResponseEntity<ApiResponse> removeRoomMember(
			@RequestParam
			@NotBlank(message = MessageConstants.Id_NOT_NULL)
	        @Pattern(regexp = ValidationConstants.ID_REGEX, message =MessageConstants.INVALID_UUID)
	 		String roomId,
			@RequestParam
			List<@NotBlank(message = MessageConstants.Id_NOT_NULL)
	        @Pattern(regexp = ValidationConstants.ID_REGEX, message =MessageConstants.INVALID_UUID)
	 		String> memberIds) {
		System.err.println("room member ids  --- > " +memberIds);
		return new ResponseEntity<ApiResponse>(chatRoomService.removeRoomMember(roomId,memberIds),HttpStatus.OK);
	}
	  
	
	@PatchMapping("/update-room-name")
	public ResponseEntity<ApiResponse> updateRoomName(
			@RequestParam
			@NotBlank(message = MessageConstants.Id_NOT_NULL)
	        @Pattern(regexp = ValidationConstants.ID_REGEX, message =MessageConstants.INVALID_UUID)
	 		String roomId,
			@RequestParam
			@NotBlank(message = MessageConstants.DROUP_NAME_REQUIRED)
		    String newName) {
		
		return new ResponseEntity<ApiResponse>(chatRoomService.updateRoomName(roomId,newName),HttpStatus.OK);
	}
	
	@GetMapping("/get-room-for-user")
	public ResponseEntity<ApiResponse> getRoomFor(@RequestParam
			@NotBlank(message = MessageConstants.Id_NOT_NULL)
            @Pattern(regexp = ValidationConstants.ID_REGEX, message =MessageConstants.INVALID_UUID)
	    	String userId) {
		
		return new ResponseEntity<ApiResponse>(chatRoomService.getRoomsForUser(userId),HttpStatus.OK);
	}
	
	
}
