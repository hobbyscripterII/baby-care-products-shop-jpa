package com.baby.babycareproductsshop.entity.product;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "t_main_category")
public class MainCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long imain;

    @Column(length = 20,name = "main_category", nullable = false)
    private String mainCategory;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

}
