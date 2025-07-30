package com.dollop.app.entity;

import com.dollop.app.utils.Auditable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Entity
public class MessageVisibility extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private Message message;

    @ManyToOne
    private User user; // the one who deleted this message

    @Builder.Default
    private boolean visible = true; // false means user deleted it
}
