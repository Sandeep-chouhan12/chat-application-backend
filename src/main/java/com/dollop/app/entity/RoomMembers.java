package com.dollop.app.entity;

import java.util.List;

import com.dollop.app.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Entity
@Table(name = "chat_room_participants")
public class RoomMembers  extends Auditable {
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JsonIgnore
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @Builder.Default
    private Boolean isAdmin = false;
   
    @Builder.Default
    private Boolean isAvailableInRoom = true;

}
