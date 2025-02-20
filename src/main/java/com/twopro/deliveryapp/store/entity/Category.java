package com.twopro.deliveryapp.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "p_category")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private UUID id;

    @Column(length = 255)
    @Setter
    private String name;
}