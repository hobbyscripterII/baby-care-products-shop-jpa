package com.baby.babycareproductsshop.entity.product;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "t_middle_category", uniqueConstraints={
        @UniqueConstraint(columnNames = {"imain", "imiddle"})
})
@Data
public class MiddleCategoryEntity {

//    @EmbeddedId
//    private CategoryIds ids;


    @ManyToOne
    @JoinColumn(name = "imain",columnDefinition = "BIGINT UNSIGNED")
    private MainCategoryEntity mainCategoryEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imiddle",columnDefinition = "BIGINT UNSIGNED",unique = true)
    private Long imiddle;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "imiddle",columnDefinition = "BIGINT UNSIGNED")
//    private Long middleCategoryEntity;

    @Column(columnDefinition = "BIGINT UNSIGNED",name = "middle_category",length = 20, nullable = false)
    private String middleCategory;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;
}
