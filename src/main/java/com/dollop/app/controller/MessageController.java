
package com.dollop.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.app.constant.MessageConstants;
import com.dollop.app.constant.ValidationConstants;
import com.dollop.app.enums.MessageStatus;
import com.dollop.app.request.RoomMessageRequest;
import com.dollop.app.response.ApiResponse;
import com.dollop.app.service.IMessageService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/chat-room")
@Validated
public class MessageController {
	@Autowired
	private IMessageService messageService;

//    @PostMapping("/send-message")
//    public ResponseEntity<ApiResponse> sendMessage(@Valid @RequestBody RoomMessageRequest request) {
//        
//    	return new ResponseEntity<ApiResponse>(messageService.sendMessage(request),HttpStatus.CREATED);
//    }

	@GetMapping("/get-message-by-roomId")
	public ResponseEntity<ApiResponse> getMessagesByRoomId(
			@RequestParam("roomId") @NotBlank(message = MessageConstants.Id_NOT_NULL) @Pattern(regexp = ValidationConstants.ID_REGEX, message = MessageConstants.INVALID_UUID) String chatRoomId) {
		System.err.println("chatRoomId ===== > " + chatRoomId);
		return new ResponseEntity<ApiResponse>(messageService.getMessagesByRoomId(chatRoomId), HttpStatus.CREATED);
	}

	@DeleteMapping("/delete-messages")
	public ResponseEntity<ApiResponse> deleteMessages(
			@RequestParam List<@NotBlank(message = MessageConstants.Id_NOT_NULL) @Pattern(regexp = ValidationConstants.ID_REGEX, message = MessageConstants.INVALID_UUID) String> messageIds) {

		return new ResponseEntity<ApiResponse>(messageService.deleteMessages(messageIds), HttpStatus.CREATED);
	}

	@DeleteMapping("/delete-message-for-me")
	public ResponseEntity<ApiResponse> deleteMessageForMe(
			@RequestBody List<@NotBlank(message = MessageConstants.Id_NOT_NULL) @Pattern(regexp = ValidationConstants.ID_REGEX, message = MessageConstants.INVALID_UUID) String> messageIds) {

		System.err.println("delete msg called .......... " +messageIds);
		return new ResponseEntity<ApiResponse>(messageService.deleteMessageForMe(messageIds), HttpStatus.CREATED);

	}
}
