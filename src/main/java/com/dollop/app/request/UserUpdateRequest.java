package com.dollop.app.request;

import com.dollop.app.constant.MessageConstants;
import com.dollop.app.constant.ValidationConstants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
//	 @NotBlank(message = MessageConstants.NAME_REQUIRED)
	 @Size(min = 2, max = 30, message = MessageConstants.OWNER_NAME_LENGTH)
	 @Pattern(regexp = ValidationConstants.NAME_REGEX,message = MessageConstants.INVALID_NAME_FORMATE)
	 private String name;

//	 @NotBlank(message =MessageConstants.EMAIL_REQUIRED)
	 @Pattern(regexp = ValidationConstants.EMAIL_REGEX,message = MessageConstants.INVALID_EMAIL_FORMATE)
     private String email;


}
