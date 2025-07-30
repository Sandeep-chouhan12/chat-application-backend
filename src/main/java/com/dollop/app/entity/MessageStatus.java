package com.dollop.app.entity;

import com.dollop.app.utils.Auditable;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class MessageStatus extends Auditable{

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	  // Receiver of the message (Many messageStatus entries can belong to one user)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id", nullable = false)
	private User receiver;
	
    //  Related message (Many messageStatus entries can belong to one message)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id")
	private Message message;
	
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private com.dollop.app.enums.MessageStatus statusType = com.dollop.app.enums.MessageStatus.SENT;
}
