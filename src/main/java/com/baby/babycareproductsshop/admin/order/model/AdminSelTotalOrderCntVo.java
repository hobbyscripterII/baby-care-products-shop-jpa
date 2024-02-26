package com.baby.babycareproductsshop.admin.order.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminSelTotalOrderCntVo {
    private LocalDate createdAt;
    private int totalOrderCnt;
    private int recallCnt;
    private int netOrderCnt;
}
