package com.baby.babycareproductsshop.entity.product;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_product")
public class ProductEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iproduct",columnDefinition = "BIGINT UNSIGNED")
    private Long iproduct;

    @ManyToOne
    @JoinColumn(columnDefinition = "BIGINT UNSIGNED",nullable = false,name = "imain")
    private MainCategoryEntity mainCategoryEntity;

    @ManyToOne
    @JoinColumn(name="imiddle", referencedColumnName="imiddle",columnDefinition = "BIGINT UNSIGNED")
    private MiddleCategoryEntity middleCategoryEntity;


    @Column(name = "product_nm", length = 150, nullable = false)
    private String productName;

    @Column(name = "product_details", length = 1000)
    private String productDetails;

    @Column(name = "recommand_age", nullable = false)
    private int recommendedAge;

    @Column(columnDefinition = "BIGINT UNSIGNED",nullable = false)
    private int price;

    @Column(name = "rep_pic", length = 1000)
    private String repPic;

    @Column(name = "remained_cnt",columnDefinition = "BIGINT UNSIGNED",nullable = false)
    private int remainedCount;

    @Column(name = "new_fl",columnDefinition = "BIGINT UNSIGNED" ,nullable = false)
    private int newFlag;

    @Column(name = "rc_fl",columnDefinition = "BIGINT UNSIGNED" ,nullable = false)
    private int rcFl;

    @Column(name = "pop_fl", columnDefinition = "BIGINT UNSIGNED",nullable = false)
    private int popFl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
