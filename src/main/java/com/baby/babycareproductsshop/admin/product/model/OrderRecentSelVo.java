package com.baby.babycareproductsshop.admin.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderRecentSelVo {
    private Long iorder;
    private String nm;
    private String addressNm;
    private String phoneNumber;
    private int processState;
    private int totalPrice;
    private LocalDateTime createdAt;


}




