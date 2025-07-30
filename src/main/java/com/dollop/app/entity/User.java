package com.dollop.app.entity;

import java.time.LocalDateTime;

import com.dollop.app.enums.UserStatus;
import com.dollop.app.utils.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "users")
public class User extends Auditable {
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

	@Column(nullable = false)	
    private String name;

	@Column(unique = true, nullable = false)
    private String userName;
	
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)	
    private String password;

    @Column(nullable = false)	
    private String profileImageUrl;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserStatus statustype = UserStatus.OFFLINE;

    @Builder.Default
    private LocalDateTime lastSeen =LocalDateTime.now();

}
