package com.dollop.app.utils;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@Data
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
public class Auditable {
      @Column(updatable = false)
	  @CreatedDate
	  @Temporal(TemporalType.TIMESTAMP)
	  private LocalDateTime createdAt;
	  @LastModifiedDate
	  @Temporal(TemporalType.TIMESTAMP)
	  private LocalDateTime updatedAt;
	  
	  @Builder.Default
	  private Boolean isDelete =false;
	  
	  @Builder.Default
	  private Boolean status = true;
	  
} 
