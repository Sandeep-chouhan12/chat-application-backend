package com.dollop.app.entity;

import java.util.List;

import com.dollop.app.enums.MessageType;
import com.dollop.app.enums.ServerType;
import com.dollop.app.utils.Auditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Entity
@Table(name = "message")
public class Message extends Auditable {
	public Message(ServerType server, String message) {
	  this.servertype =server;
	  this.content =message;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private ChatRoom chatRoom;

    @ManyToOne
    private User sender;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String mediaUrl; // If it's an image/video/file
 
    @OneToMany(mappedBy ="message" ,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<MessageStatus> statusType;
    
    @Enumerated(EnumType.STRING)
    private ServerType servertype;
    
    @ManyToOne
    private Message replyMessage;
}
