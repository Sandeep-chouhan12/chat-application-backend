package com.dollop.app.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomMembersResponse {
    private String id;

    private UserResponse user;
    
    private Boolean isAdmin;
    
    private  Boolean isAvailableInRoom ;
}
