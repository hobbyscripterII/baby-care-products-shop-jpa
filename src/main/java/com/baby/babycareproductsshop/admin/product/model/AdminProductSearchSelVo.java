package com.baby.babycareproductsshop.admin.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductSearchSelVo {
    private String productNm;
    private Long iproduct;
    private int price;
    private String repPic;
    private Long imain;
    private Long imiddle;


}
