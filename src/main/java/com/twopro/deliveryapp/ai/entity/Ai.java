package com.twopro.deliveryapp.ai.entity;

import com.twopro.deliveryapp.ai.dto.SaveAiServiceRequestDto;
import com.twopro.deliveryapp.common.entity.BaseEntity;
import com.twopro.deliveryapp.menu.entity.Menu;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(length = 255, nullable = false)
    private String question;

    @Column(length = 255, nullable = false)
    private String aiAnswer;

    public static Ai from(SaveAiServiceRequestDto saveAiServiceRequestDto) {
        return Ai.builder()
                .menu(saveAiServiceRequestDto.menu())
                .question(saveAiServiceRequestDto.question())
                .aiAnswer(saveAiServiceRequestDto.aiAnswer())
                .build();
    }
}
