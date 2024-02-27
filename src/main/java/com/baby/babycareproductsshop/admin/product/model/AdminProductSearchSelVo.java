package com.baby.babycareproductsshop.admin.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminProductSearchSelVo {
    private String repPic;
    private Long iproduct;
    private String productNm;
    private int price;

}
