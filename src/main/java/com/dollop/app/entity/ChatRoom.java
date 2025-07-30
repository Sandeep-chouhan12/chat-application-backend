package com.dollop.app.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.dollop.app.utils.Auditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
@Table(name="chat_rooms")
public class ChatRoom extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private boolean isGroup;

    @Column(nullable = false)
    private String roomName; // For group chat

    @ManyToOne
    private User createdBy;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL , fetch = FetchType.EAGER,orphanRemoval = true)
    private List<RoomMembers> members;
}
