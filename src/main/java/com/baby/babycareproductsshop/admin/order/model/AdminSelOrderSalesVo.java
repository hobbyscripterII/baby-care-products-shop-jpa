package com.baby.babycareproductsshop.admin.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminSelOrderSalesVo implements StatisticsVo{
    private int totalSales;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private int processState;
    @JsonIgnore
    private int deleteFl;

    private int earnings;
    private int costPrice;
    private String date;

}
