package com.baby.babycareproductsshop.admin.product.model;

import lombok.Data;

@Data
public class ProductGetSearchDto {
    private String keyword;

    private Long iproduct;

    private Long imain;

    private Long imiddle;
    private int minPrice;
    private int maxPrice;
    private int dateFl;
//    private int startDate;
//    private int endDate;

}
