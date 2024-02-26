package com.baby.babycareproductsshop.admin.order.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminSelTotalOrderCntVo {
    private int totalOrderCnt;
    private LocalDateTime createdAt;
    private int recallCnt;
    private int netOrderCnt;
}
