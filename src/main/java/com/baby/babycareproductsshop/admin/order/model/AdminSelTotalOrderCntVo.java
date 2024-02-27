package com.baby.babycareproductsshop.admin.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminSelTotalOrderCntVo implements StatisticsVo{
    private int totalOrderCnt;
    @JsonIgnore
    private LocalDateTime createdAt;
    private int recallCnt;
    private int netOrderCnt;
    private String date;
}
