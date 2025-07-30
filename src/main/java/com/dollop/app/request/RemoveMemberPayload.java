package com.dollop.app.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemoveMemberPayload {

	  private String roomId;
	  private List<String> memberIds;

}
