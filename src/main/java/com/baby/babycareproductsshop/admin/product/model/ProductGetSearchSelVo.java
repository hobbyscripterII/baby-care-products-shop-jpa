package com.baby.babycareproductsshop.admin.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGetSearchSelVo {
    private String productNm;
    private Long iproduct;
    private int price;
    private Long imain;
    private Long imiddle;
    private String repPic;

}
