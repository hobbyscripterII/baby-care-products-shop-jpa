package com.baby.babycareproductsshop.admin.product.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductGetSearchDto {
    private String keyword;

    private Long iproduct;

    private Long imain;

    private Long imiddle;
    private int minPrice;
    private int maxPrice;
    private int dateFl;
    private LocalDate searchStartDate;
    private LocalDate searchEndDate;



}
