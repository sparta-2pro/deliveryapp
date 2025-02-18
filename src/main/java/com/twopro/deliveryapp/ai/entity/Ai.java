package com.twopro.deliveryapp.ai.entity;

import com.twopro.deliveryapp.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "P_AI")
@Builder @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ai extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID aiId;

    @Column(length = 255, nullable = false)
    private String question;

    @Column(length = 255, nullable = false)
    private String aiAnswer;
}
