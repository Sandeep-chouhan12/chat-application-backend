package com.dollop.app.request;

import java.util.List;

import com.dollop.app.constant.MessageConstants;
import com.dollop.app.constant.ValidationConstants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMemberRequest {
 private String roomId;
 private List<@NotBlank(message = MessageConstants.Id_NOT_NULL)
 @Pattern(regexp = ValidationConstants.ID_REGEX, message =MessageConstants.INVALID_UUID)
	String> userIds;
}
