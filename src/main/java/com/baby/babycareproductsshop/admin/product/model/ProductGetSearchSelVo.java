package com.baby.babycareproductsshop.admin.product.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductGetSearchSelVo {
    private String productNm;
    private Long iproduct;
    private int price;
    private Long imain;
    private Long imiddle;
    private String repPic;

}
